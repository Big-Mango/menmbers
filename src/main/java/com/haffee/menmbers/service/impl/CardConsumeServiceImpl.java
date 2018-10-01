package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.repository.*;
import com.haffee.menmbers.service.CardConsumeService;
import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.SmsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
     * 2.校验优惠券
     * 3.更新账户
     *
     * @param cardConsume
     * @return
     */
    public CardConsume add(CardConsume cardConsume) {
        CardConsume responseCardConsume = null;
        //根据userPhone获取user信息
        User user = userRepository.findByUserPhone(cardConsume.getUserPhone());
        if (user != null) {

            Card card = cardRepository.findByCardNo(cardConsume.getCardNo());
            //1.校验余额
            if (card.getBalance() < cardConsume.getPayFee()) {
                return null;
            }
            //2.校验优惠券
            if (StringUtils.isNotEmpty(cardConsume.getUser_coupons_id())) {
                Coupons c = couponsRepository.findEnableCouponsByUserAndShopAndId(Integer.valueOf(cardConsume.getUser_coupons_id()), user.getId(), cardConsume.getShopId(), cardConsume.getShould_pay());
                if(null!=c){
                    if(cardConsume.getShould_pay()!=(cardConsume.getPayFee()+c.getCoupon_value())){ //优惠券金额+实际支付金额 不等于 应收金额
                        return null;
                    }
                    cardConsume.setDiscountFee(c.getCoupon_value());
                    cardConsume.setDiscountId(c.getId());
                    cardConsume.setIfDiscount(1);
                    cardConsume.setDiscountDesc("使用"+c.getCoupon_value()+"元优惠券");
                    c.setUseStaus(1);
                    couponsRepository.save(c);
                }else{
                    return null;
                }

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
                    sms_content.append(a[0] + cardConsume.getPayFee() + a[1]);
                    SmsUtils.singleSend(cardConsume.getUserPhone(), sms_content.toString());
                }
            }
        }
        return responseCardConsume;
    }

    public Optional<CardConsume> findById(int id) {
        return cardConsumeRepository.findById(id);
    }
}
