package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardConsume;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.repository.CardConsumeRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.CardConsumeService;
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
public class CardConsumeServiceImpl implements CardConsumeService {

    @Resource
    private CardConsumeRepository cardConsumeRepository;

    @Resource
    private UserRepository userRepository;

    public Page<CardConsume> findAll(Pageable pageable) {
        return cardConsumeRepository.findAll(pageable);
    }

    public Page<CardConsume> findByCardNo(String cardNo, Pageable pageable) {
        return cardConsumeRepository.findByCardNo(cardNo,pageable);
    }

    public CardConsume add(CardConsume cardConsume) {
        CardConsume responseCardConsume = null;
        //根据cardNo获取user信息
        User user = userRepository.getUserByCardNo(cardConsume.getCardNo());
        if(user!=null){
            //保存消费记录
            cardConsume.setCardId(user.getCardId());
            cardConsume.setShopId(user.getShopId());
            cardConsume.setUserId(user.getId());
            cardConsume.setUserPhone(user.getUserPhone());
            responseCardConsume = cardConsumeRepository.save(cardConsume);
            //更新用户卡余额
            user.setBalance(user.getBalance()-cardConsume.getPayFee());
            userRepository.save(user);
        }
        return responseCardConsume;
    }

    public Optional<CardConsume> findById(int id){
        return cardConsumeRepository.findById(id);
    }
}
