package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.repository.*;
import com.haffee.menmbers.service.CardConsumeService;
import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.HttpClientUtils;
import com.haffee.menmbers.utils.OrderNumUtils;
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
public class CardConsumeServiceImpl implements CardConsumeService {

    @Resource
    private CardConsumeRepository cardConsumeRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private PersonRepository personRepository;

    @Resource
    private CardRepository cardRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CouponsRepository couponsRepository;

    @Autowired
    private RealDiscountRepository realDiscountRepository;

    @Autowired
    private ManjianConfigRepository manjianConfigRepository;

    @Autowired
    private JifenConfigRepository jifenConfigRepository;

    @Autowired
    private RealDiscountLogRepository realDiscountLogRepository;

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    public Page<CardConsume> findAllByShopId(Pageable pageable, int shopId) {
        Page<CardConsume> page = cardConsumeRepository.findByShopId(shopId, pageable);
        if (page != null) {
            List<CardConsume> list = page.getContent();
            for (CardConsume cardConsume : list) {
                User user = userRepository.findByUserPhone(cardConsume.getUserPhone());
                if (user != null) {
                    Optional<Person> optionalPerson = personRepository.findById(user.getPersonId());
                    if (optionalPerson.isPresent()) {
                        user.setPerson(optionalPerson.get());
                    }
                    Optional<Card> optionalCard = cardRepository.findById(cardConsume.getCardId());
                    if (optionalCard.isPresent()) {
                        user.setCard(optionalCard.get());
                    }
                    cardConsume.setUser(user);
                }
            }
        }
        return page;
    }

    public Page<CardConsume> findByCardNo(String cardNo, Pageable pageable) {
        return cardConsumeRepository.findByCardNo(cardNo, pageable);
    }

    public Page<CardConsume> findByUserPhone(String userPhone, Pageable pageable) {
        Page<CardConsume> page = cardConsumeRepository.findByUserPhone(userPhone, pageable);
        if (null != page) {
            List<CardConsume> list = page.getContent();
            for (CardConsume cc : list) {
                Optional<Shop> o = shopRepository.findById(cc.getShopId());
                if (o.isPresent()) {
                    cc.setShop(o.get());
                }
            }
        }
        return page;
    }

    /**
     * 1.校验余额
     * 2.校验优惠
     * 3.校验优惠券
     * 4.更新账户
     *
     * @param cardConsume
     * @return
     */
    public CardConsume add(CardConsume cardConsume,String yh_id) {
        CardConsume responseCardConsume = null;
        //根据userPhone获取user信息
        User user = userRepository.findByUserPhone(cardConsume.getUserPhone());
        if (user != null) {

            Card card = cardRepository.findByCardNo(cardConsume.getCardNo());
            //1.校验余额
            if (card.getBalance() < cardConsume.getPayFee()) {
                return null;
            }

            //2.校验优惠
            if(StringUtils.isNotEmpty(yh_id)){
                if(yh_id.split("_")[0].equals("manjian")){
                    ManjianConfig manjian = manjianConfigRepository.findOneEnableByShopId(cardConsume.getShopId()+"");
                    if(null!=manjian&&manjian.getId()==Integer.valueOf(yh_id.split("_")[1])){
                        if(cardConsume.getShould_pay()>=manjian.getMan()){
                            cardConsume.setDiscountFee(cardConsume.getShould_pay()-cardConsume.getPayFee());
                            cardConsume.setIfDiscount(1);
                            cardConsume.setDiscountDesc((cardConsume.getDiscountDesc()==null?"":cardConsume.getDiscountDesc())+" "+"参与满减优惠"+manjian.getJian()+"元");
                            cardConsume.setDiscountId((cardConsume.getDiscountId()==null?"":cardConsume.getDiscountId())+" "+"manjian_"+manjian.getId());
                        }else{
                            return null;
                        }
                    }else{
                        return null;
                    }
                }
                if(yh_id.split("_")[0].equals("realdiscount")){
                    RealDiscountConfig rdc = realDiscountRepository.findOneByShop(cardConsume.getShopId()+"");
                    if(null!=rdc&&rdc.getId()==Integer.valueOf(yh_id.split("_")[1])){
                        cardConsume.setDiscountFee(cardConsume.getShould_pay()-cardConsume.getPayFee());
                        cardConsume.setIfDiscount(1);
                        cardConsume.setDiscountDesc((cardConsume.getDiscountDesc()==null?"":cardConsume.getDiscountDesc())+" "+"打"+rdc.getDiscountValue()+"%折");
                        cardConsume.setDiscountId((cardConsume.getDiscountId()==null?"":cardConsume.getDiscountId())+" "+"discount_"+rdc.getId());
                        //记录打折次数
                        RealDiscountLog log = new RealDiscountLog();
                        log.setCard_no(cardConsume.getCardNo());
                        log.setShop_id(cardConsume.getShopId()+"");
                        log.setUser_id(user.getId()+"");
                        log.setUse_time(new Date());
                        realDiscountLogRepository.save(log);
                    }
                }
            }


            //3.校验优惠券
            if (StringUtils.isNotEmpty(cardConsume.getUser_coupons_id())) {
                Coupons c = couponsRepository.findEnableCouponsByUserAndShopAndId(Integer.valueOf(cardConsume.getUser_coupons_id()), user.getId(), cardConsume.getShopId(), cardConsume.getShould_pay());
                if(null!=c){
//                    if(cardConsume.getShould_pay()!=(cardConsume.getPayFee()+c.getCoupon_value())){ //优惠券金额+实际支付金额 不等于 应收金额
//                        return null;
//                    }
                    cardConsume.setDiscountFee(cardConsume.getShould_pay()-cardConsume.getPayFee());
                    cardConsume.setDiscountId(cardConsume.getDiscountId()+" "+"coupons_"+c.getId());
                    cardConsume.setIfDiscount(1);
                    cardConsume.setDiscountDesc(cardConsume.getDiscountDesc()+" 使用"+c.getCoupon_value()+"元优惠券");
                    c.setUseStaus(1);
                    couponsRepository.save(c);
                }else{
                    return null;
                }

            }

            //4.校验积分配置
            JifenConfig jifen_config = jifenConfigRepository.findOneEnableByShopId(cardConsume.getShopId()+"");
            if(null!=jifen_config){
                card.setJifen(card.getJifen()+((int)cardConsume.getPayFee())*jifen_config.getJifen());
            }

            //更新用户卡余额
            card.setBalance(card.getBalance() - cardConsume.getPayFee());
            cardRepository.save(card);
            //保存消费记录
            cardConsume.setCardId(card.getId());
            cardConsume.setCardNo(cardConsume.getCardNo());
            cardConsume.setShopId(cardConsume.getShopId());
            cardConsume.setUserId(user.getId());
            cardConsume.setUserPhone(user.getUserPhone());
            cardConsume.setPayFee(cardConsume.getPayFee());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(new Date());
            cardConsume.setCreateTime(createTime);
            responseCardConsume = cardConsumeRepository.save(cardConsume);
            if (responseCardConsume != null) {
                //发送消费消息通知
                StringBuffer sms_content = new StringBuffer();
                String sms_content_template = ConfigUtils.getPerson_consume();
                if (null != sms_content_template) {
                    //拼接短信内容
                    String[] a = sms_content_template.split("&");
                    Optional<Person> o = personRepository.findById(user.getId());
                    String real_name = "";
                    if(o.isPresent()){
                        real_name = o.get().getRealName();
                    }
                    sms_content.append(a[0] + real_name + a[1]+cardConsume.getPayFee()+a[2]+card.getBalance()+a[3]);
                    SmsUtils.singleSend(cardConsume.getUserPhone(), sms_content.toString());
                }


                Optional<Shop> o = shopRepository.findById(card.getShopId());
                if(o.isPresent()&&StringUtils.isNotEmpty(user.getWechatId())){
                    //发送微信通知
                    Map<String,NoticeItem> map = new HashMap<>();

                    NoticeItem item1 = new NoticeItem(); //标题
                    item1.setValue("您有一笔会员卡消费！");
                    item1.setColor("#173177");
                    map.put("first",item1);

                    Shop s = o.get();
                    NoticeItem item2 = new NoticeItem(); //店铺名称
                    item2.setValue(s.getShopName());
                    item2.setColor("#173177");
                    map.put("keyword2",item2);

                    NoticeItem item3 = new NoticeItem();
                    item3.setValue(card.getCardNo());
                    item3.setColor("#173177");
                    map.put("keyword1",item3);

                    NoticeItem item4 = new NoticeItem();
                    item4.setValue(cardConsume.getPayFee()+"");
                    item4.setColor("#173177");
                    map.put("keyword3",item4);

                    NoticeItem item7 = new NoticeItem();
                    item7.setValue("期待您欢迎再次光临！");
                    item7.setColor("#173177");
                    map.put("remark",item7);

                    WechatNoticeForm form = new WechatNoticeForm();
                    form.setTouser(user.getWechatId()); //openid
                    form.setTemplate_id(ConfigUtils.getSysConfig("wn_temp_id_consume")); //模板ID
                    form.setUrl(ConfigUtils.getSysConfig("wn_temp_url_consume")); // 详情URL
                    form.setData(map);
                    String access_token = WXAccessToken.getAccessToken();
                    String result = HttpClientUtils.doPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,JSONObject.fromObject(form).toString());
                    System.out.println("微信通知结果："+result);
                }

            }
        }
        return responseCardConsume;
    }

    public Optional<CardConsume> findById(int id) {
        return cardConsumeRepository.findById(id);
    }

    /**
     * 发送微信订单通知
     * @param cardConsume
     * @param yh_id
     */
    @Override
    public void sendOrderNotice(CardConsume cardConsume, String yh_id,String diancai_order_id) {
        User user = userRepository.findByUserPhone(cardConsume.getUserPhone());
        if(null!=user){
            PaymentOrder order = new PaymentOrder();
            order.setOrder_no(OrderNumUtils.genOrderNum());
            order.setUser_id(user.getId()+"");
            order.setShop_id(cardConsume.getShopId()+"");
            order.setCard_no(cardConsume.getCardNo());
            order.setStatus(0);
            order.setPayment(cardConsume.getPayFee()); //计算优惠
            order.setYouhui_content(""); //拼接优惠内容
            order.setOrder_content("会员卡消费"+order.getPayment()+"元");
            order.setCreate_time(new Date());
            if(StringUtils.isNotEmpty(diancai_order_id)){
                order.setDiancai_order_id(diancai_order_id);
            }


            //发送通知
            Map<String,NoticeItem> map = new HashMap<>();

            NoticeItem item1 = new NoticeItem(); //标题
            item1.setValue("您有一笔待支付订单！");
            item1.setColor("#173177");
            map.put("first",item1);

            NoticeItem item2 = new NoticeItem();
            item2.setValue(order.getOrder_no());
            item2.setColor("#173177");
            map.put("keyword1",item2);

            NoticeItem item3 = new NoticeItem();
            item3.setValue("待支付");
            item3.setColor("#173177");
            map.put("keyword2",item3);

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            NoticeItem item4 = new NoticeItem();
            item4.setValue(sdf.format(order.getCreate_time()));
            item4.setColor("#173177");
            map.put("keyword3",item4);

            NoticeItem item5 = new NoticeItem();
            item5.setValue(order.getOrder_content());
            item5.setColor("#173177");
            map.put("keyword4",item5);

            NoticeItem item6 = new NoticeItem();
            item6.setValue("点击支付，祝您生活愉快！");
            item6.setColor("#173177");
            map.put("remark",item6);

            WechatNoticeForm form = new WechatNoticeForm();
            form.setTouser(user.getWechatId()); //openid
            form.setTemplate_id(ConfigUtils.getSysConfig("wn_temp_id_order")); //模板ID
            form.setUrl(ConfigUtils.getSysConfig("wn_temp_url_order")); // 详情URL
            form.setData(map);
            String access_token = WXAccessToken.getAccessToken();
            HttpClientUtils.doPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,JSONObject.fromObject(form).toString());
            paymentOrderRepository.save(order);
        }

    }


}
