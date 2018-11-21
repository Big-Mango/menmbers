package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.repository.*;
import com.haffee.menmbers.service.CardRechargeService;
import com.haffee.menmbers.utils.CardTypeUtils;
import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.HttpClientUtils;
import com.haffee.menmbers.utils.SmsUtils;
import com.haffee.menmbers.utils.wxpay.WXAccessToken;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: java类作用描述
 * @Author: liujia
 * @CreateDate: 2018/7/29 10:25
 * @Version: 1.0
 */
@Service
@Transactional
public class CardRechargeServiceImpl implements CardRechargeService {

    @Resource
    private CardRechargeRepository cardRechargeRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private PersonRepository personRepository;

    @Resource
    private CardRepository cardRepository;

    @Resource
    private DiscountConfigRepository discountConfigRepository;

    @Autowired
    private ShopRepository shopRepository; //add by jacktong

    public Page<CardRecharge> findAllByShopId(Pageable pageable,int shopId) {
        Page<CardRecharge> page = cardRechargeRepository.findByShopId(shopId,pageable);
        if (page != null) {
            List<CardRecharge> list = page.getContent();
            for (CardRecharge cardRecharge : list) {
                Optional<User> user = userRepository.findById(cardRecharge.getUserId());
                if(user.isPresent()){
                    Optional<Person> optionalPerson = personRepository.findById(user.get().getPersonId());
                    if (optionalPerson.isPresent()) {
                        user.get().setPerson(optionalPerson.get());
                    }
                    Optional<Card> optionalCard = cardRepository.findById(cardRecharge.getCardId());
                    if (optionalCard.isPresent()) {
                        user.get().setCard(optionalCard.get());
                    }
                    cardRecharge.setUser(user.get());
                }
            }
        }
        return page;
    }

    public Page<CardRecharge> findByCardNo(String cardNo,Pageable pageable) {
        return cardRechargeRepository.findByCardNo(cardNo,pageable);
    }

    public Page<CardRecharge> findByUserPhone(String userPhone,Pageable pageable){
        Page<CardRecharge> page = cardRechargeRepository.findByUserPhone(userPhone,pageable);
        if (page != null) {
            List<CardRecharge> list = page.getContent();
            for (CardRecharge cardRecharge : list) {
                User user = userRepository.getUserByCardNo(cardRecharge.getCardNo());
                if(user!=null){
                    Optional<Person> optionalPerson = personRepository.findById(user.getPersonId());
                    if (optionalPerson.isPresent()) {
                        user.setPerson(optionalPerson.get());
                    }
                    //modify by jacktong 2018-9-6
                    Optional<Card> optionalCard = cardRepository.findById(cardRecharge.getCardId());
                    if (optionalCard.isPresent()) {
                        Card c = optionalCard.get();
                        Optional<Shop> o = shopRepository.findById(c.getShopId());
                        if(o.isPresent()){
                            c.setShop(o.get());
                        }
                        user.setCard(c);
                    }
                    cardRecharge.setUser(user);
                }
            }
        }
        return page;
    }

    public CardRecharge add(CardRecharge cardRecharge) {
        CardRecharge responseCardRecharge = null;
        //根据cardNo获取user信息
        User user = userRepository.getUserByCardNo(cardRecharge.getCardNo());
        Card card = cardRepository.findByCardNo(cardRecharge.getCardNo());
        if(user!=null){
            //取折扣信息
            float discountFee = 0;
            int discountId = 0 ;
            String discountDesc = "";

            //获取最匹配的优惠方案
            List<DiscountConfig> list = discountConfigRepository.findByFullMoney(cardRecharge.getFee(),cardRecharge.getShopId());
            HashMap<Float,Integer> map = new HashMap();
            //将fee与每一个方案的折扣价格做差，取绝对值(其实正常不取绝对值也是个大于等于0的数)
            for(DiscountConfig discountConfig : list){
                //判断是否符合卡类型
                if(CardTypeUtils.if_card_type_contain(card.getCardType()+"",discountConfig.getCardType())){
                    map.put(Math.abs(cardRecharge.getFee()-discountConfig.getFullMoney()),discountConfig.getId());
                }

            }
            if(!map.isEmpty()){
                //此处用Collections.min方法取集合中的最小值
                float rightKey = Collections.min(map.keySet()).floatValue();
                //取最合适方案的id
                int rightId = map.get(rightKey);
                Optional<DiscountConfig> discountConfig = discountConfigRepository.findById(rightId);
                if(discountConfig.isPresent()){
                    discountId = discountConfig.get().getId();
                    discountFee = discountConfig.get().getAddMoney();
                    discountDesc = discountConfig.get().getName();
                }
            }

            //保存充值记录
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(new Date());
            cardRecharge.setCreateTime(createTime);
            cardRecharge.setPaymentTime(createTime);
            cardRecharge.setCardId(card.getId());
            cardRecharge.setUserId(user.getId());
            cardRecharge.setShopId(card.getShopId());
            cardRecharge.setUserPhone(user.getUserPhone());
            cardRecharge.setDiscountDesc(discountDesc);
            cardRecharge.setDiscountId(discountId);
            cardRecharge.setDiscountFee(discountFee);
            responseCardRecharge = cardRechargeRepository.save(cardRecharge);
            //更新用户冻结状态，
            user.setStatus(1);
            userRepository.save(user);
            //更新用户卡余额
            card.setBalance(card.getBalance()+cardRecharge.getFee()+cardRecharge.getDiscountFee());
            Card cardResponse = cardRepository.save(card);
            if(cardResponse!=null) {
                //发送充值消息通知
                StringBuffer sms_content = new StringBuffer();
                String sms_content_template = ConfigUtils.getPerson_recharge();
                if (null != sms_content_template) {
                    //拼接短信内容
                    String[] a = sms_content_template.split("&");
                    sms_content.append(a[0] + cardRecharge.getFee()+ discountFee + a[1]);
                    SmsUtils.singleSend(user.getUserPhone(), sms_content.toString());
                }

                //微信通知
                Optional<Shop> o = shopRepository.findById(card.getShopId());
                if(o.isPresent()&&StringUtils.isNotEmpty(user.getWechatId())){
                    //发送微信通知
                    Shop s = o.get();
                    Map<String,NoticeItem> map_param = new HashMap<>();

                    NoticeItem item1 = new NoticeItem(); //标题
                    item1.setValue("您有一笔"+s.getShopName()+"会员卡充值！");
                    item1.setColor("#173177");
                    map_param.put("first",item1);


                    NoticeItem item2 = new NoticeItem(); //店铺名称
                    item2.setValue("会员卡号");
                    item2.setColor("#173177");
                    map_param.put("accountType",item2);

                    NoticeItem item3 = new NoticeItem();
                    item3.setValue(card.getCardNo());
                    item3.setColor("#173177");
                    map_param.put("account",item3);

                    NoticeItem item4 = new NoticeItem();
                    item4.setValue(cardRecharge.getFee()+"");
                    item4.setColor("#173177");
                    map_param.put("amount",item4);

                    NoticeItem item5 = new NoticeItem();
                    item5.setValue("充值成功");
                    item5.setColor("#173177");
                    map_param.put("result",item5);

                    NoticeItem item7 = new NoticeItem();
                    item7.setValue("欢迎再次光临！");
                    item7.setColor("#173177");
                    map_param.put("remark",item7);

                    WechatNoticeForm form = new WechatNoticeForm();
                    form.setTouser(user.getWechatId()); //openid
                    form.setTemplate_id(ConfigUtils.getSysConfig("wn_temp_id_recharge")); //模板ID
                    form.setUrl(ConfigUtils.getSysConfig("wn_temp_url_recharge")); // 详情URL
                    form.setData(map_param);
                    String access_token = WXAccessToken.getAccessToken();
                    String result = HttpClientUtils.doPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,JSONObject.fromObject(form).toString());
                    System.out.println("微信通知结果："+result);
                }

            }
        }
        return responseCardRecharge;
    }

    public Optional<CardRecharge> findById(int id){
        return cardRechargeRepository.findById(id);
    }

    @Override
    public CardRecharge findOneByOrderNo(String order_no) {
        return cardRechargeRepository.findOneByOrderno(order_no);
    }

    @Override
    public CardRecharge save(CardRecharge cr) {
        return cardRechargeRepository.save(cr);
    }

    @Override
    public CardRecharge genRechargeOrder(CardRecharge cardRecharge) {
        CardRecharge responseCardRecharge = null;
        //根据cardNo获取user信息
        User user = userRepository.getUserByCardNo(cardRecharge.getCardNo());
        Card card = cardRepository.findByCardNo(cardRecharge.getCardNo());
        if(user!=null){
            //取折扣信息
            float discountFee = 0;
            int discountId = 0 ;
            String discountDesc = "";

            //获取最匹配的优惠方案
            List<DiscountConfig> list = discountConfigRepository.findByFullMoney(cardRecharge.getFee(),cardRecharge.getShopId());
            HashMap<Float,Integer> map = new HashMap();
            //将fee与每一个方案的折扣价格做差，取绝对值(其实正常不取绝对值也是个大于等于0的数)
            for(DiscountConfig discountConfig : list){
                map.put(Math.abs(cardRecharge.getFee()-discountConfig.getFullMoney()),discountConfig.getId());
            }
            if(!map.isEmpty()){
                //此处用Collections.min方法取集合中的最小值
                float rightKey = Collections.min(map.keySet()).floatValue();
                //取最合适方案的id
                int rightId = map.get(rightKey);
                Optional<DiscountConfig> discountConfig = discountConfigRepository.findById(rightId);
                if(discountConfig.isPresent()){
                    discountId = discountConfig.get().getId();
                    discountFee = discountConfig.get().getAddMoney();
                    discountDesc = discountConfig.get().getName();
                }
            }

            //保存充值记录
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(new Date());
            cardRecharge.setCreateTime(createTime);
            cardRecharge.setPaymentTime(createTime);
            cardRecharge.setCardId(card.getId());
            cardRecharge.setUserId(user.getId());
            cardRecharge.setShopId(card.getShopId());
            cardRecharge.setUserPhone(user.getUserPhone());
            cardRecharge.setDiscountDesc(discountDesc);
            cardRecharge.setDiscountId(discountId);
            cardRecharge.setDiscountFee(discountFee);
            responseCardRecharge = cardRechargeRepository.save(cardRecharge);
            //更新用户冻结状态，
//            user.setStatus(1);
//            userRepository.save(user);
            //更新用户卡余额
//            card.setBalance(card.getBalance()+cardRecharge.getFee()+cardRecharge.getDiscountFee());
//            Card cardResponse = cardRepository.save(card);
//            if(cardResponse!=null) {
//                //发送充值消息通知
//                StringBuffer sms_content = new StringBuffer();
//                String sms_content_template = ConfigUtils.getPerson_recharge();
//                if (null != sms_content_template) {
//                    //拼接短信内容
//                    String[] a = sms_content_template.split("&");
//                    sms_content.append(a[0] + cardRecharge.getFee()+ discountFee + a[1]);
//                    SmsUtils.singleSend(user.getUserPhone(), sms_content.toString());
//                }
//            }
        }
        return responseCardRecharge;
    }


}
