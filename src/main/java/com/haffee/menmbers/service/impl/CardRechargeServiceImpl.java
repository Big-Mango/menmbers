package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.repository.*;
import com.haffee.menmbers.service.CardRechargeService;
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
//                    Optional<Card> optionalCard = cardRepository.findById(user.getCardId());
//                    if (optionalCard.isPresent()) {
//                        user.setCard(optionalCard.get());
//                    }
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
            List<DiscountConfig> list = discountConfigRepository.findByFullMoney(cardRecharge.getFee());
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
            user.setStatus(1);
            userRepository.save(user);
            //更新用户卡余额
            card.setBalance(card.getBalance()+cardRecharge.getFee()+cardRecharge.getDiscountFee());
            cardRepository.save(card);
        }
        return responseCardRecharge;
    }

    public Optional<CardRecharge> findById(int id){
        return cardRechargeRepository.findById(id);
    }
}
