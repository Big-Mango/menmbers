package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardRecharge;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.repository.CardRechargeRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.CardRechargeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

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

    public Page<CardRecharge> findAll(Pageable pageable) {
        return cardRechargeRepository.findAll(pageable);
    }

    public Page<CardRecharge> findByCardNo(String cardNo,Pageable pageable) {
        return cardRechargeRepository.findByCardNo(cardNo,pageable);
    }

    public CardRecharge add(CardRecharge cardRecharge) {
        CardRecharge responseCardRecharge = null;
        //根据cardNo获取user信息
        User user = userRepository.getUserByCardNo(cardRecharge.getCardNo());
        if(user!=null){
            //保存充值记录
            responseCardRecharge = cardRechargeRepository.save(cardRecharge);
            //保存卡历史记录
//            CardConsume cardConsume = new CardConsume();
//            cardConsume.setType(8);//1:消费，8：充值
//            cardConsume.setUserId(user.getId());
//            cardConsume.setUserPhone(user.getUserPhone());
//            cardConsume.setCardId(user.getCardId());
//            cardConsume.setCardNo(cardRecharge.getCardNo());
//            cardConsume.setPayFee(cardRecharge.getFee());//充值金额
//            cardConsume.setBalance(cardRecharge.getFee()+cardRecharge.getDiscountFee());//算上优惠活动的总金额
//            if(cardRecharge.getDiscountId()>0){
//                cardConsume.setIfDiscount(1);
//                cardConsume.setDiscountId(cardRecharge.getDiscountId());
//                cardConsume.setDiscountFee(cardRecharge.getDiscountFee());
//                cardConsume.setDiscountDesc("");
//            }
//            cardConsume.setCreateTime(new Date());
//            cardConsume.setPaymentWay(0);
//            cardConsume.setShopId(cardRecharge.getShopId());
//            cardConsume.setOrderId("");
//            cardConsume.setRechargeId(cardRecharge.getId());
            //更新用户冻结状态，更新用户卡余额
            user.setStatus(1);
            user.setBalance(user.getBalance()+cardRecharge.getFee()+cardRecharge.getDiscountFee());
            userRepository.save(user);
        }
        return responseCardRecharge;
    }

    public Optional<CardRecharge> findById(int id){
        return cardRechargeRepository.findById(id);
    }
}
