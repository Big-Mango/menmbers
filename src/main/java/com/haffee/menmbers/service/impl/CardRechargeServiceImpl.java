package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardRecharge;
import com.haffee.menmbers.entity.CardRecord;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.repository.CardRechargeRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.CardRechargeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
            CardRecord cardRecord = new CardRecord();
            cardRecord.setType(8);//1:消费，8：充值
            cardRecord.setUserId(user.getId());
            cardRecord.setUserPhone(user.getUserPhone());
            cardRecord.setCardId(user.getCardId());
            cardRecord.setCardNo(cardRecharge.getCardNo());
            cardRecord.setPayFee(cardRecharge.getFee());//充值金额
            cardRecord.setBalance(cardRecharge.getFee()+cardRecharge.getDiscountFee());//算上优惠活动的总金额
            if(cardRecharge.getDiscountId()>0){
                cardRecord.setIfDiscount(1);
                cardRecord.setDiscountId(cardRecharge.getDiscountId());
                cardRecord.setDiscountFee(cardRecharge.getDiscountFee());
                cardRecord.setDiscountDesc("");
            }
            cardRecord.setCreateTime(new Date());
            cardRecord.setPaymentWay(0);
            cardRecord.setShopId(cardRecharge.getShopId());
            cardRecord.setOrderId("");
            cardRecord.setRechargeId(cardRecharge.getId());
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
