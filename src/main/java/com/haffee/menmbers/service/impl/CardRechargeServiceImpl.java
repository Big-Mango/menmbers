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
import java.text.SimpleDateFormat;
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

    public Page<CardRecharge> findByUserPhone(String userPhone,Pageable pageable){
        return cardRechargeRepository.findByUserPhone(userPhone,pageable);
    }

    public CardRecharge add(CardRecharge cardRecharge) {
        CardRecharge responseCardRecharge = null;
        //根据cardNo获取user信息
        User user = userRepository.getUserByCardNo(cardRecharge.getCardNo());
        if(user!=null){
            //保存充值记录
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(new Date());
            cardRecharge.setCreateTime(createTime);
            cardRecharge.setPaymentTime(createTime);
            cardRecharge.setCardId(user.getCardId());
            cardRecharge.setUserId(user.getId());
            cardRecharge.setShopId(user.getShopId());
            cardRecharge.setUserPhone(user.getUserPhone());
            responseCardRecharge = cardRechargeRepository.save(cardRecharge);
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
