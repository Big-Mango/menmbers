package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.repository.CardConsumeRepository;
import com.haffee.menmbers.repository.CardRepository;
import com.haffee.menmbers.repository.PersonRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.CardConsumeService;
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

    public Page<CardConsume> findAll(Pageable pageable) {
        Page<CardConsume> page = cardConsumeRepository.findAll(pageable);
        if (page != null) {
            List<CardConsume> list = page.getContent();
            for (CardConsume cardConsume : list) {
                User user = userRepository.getUserByCardNo(cardConsume.getCardNo());
                if(user!=null){
                    Optional<Person> optionalPerson = personRepository.findById(user.getPersonId());
                    if (optionalPerson.isPresent()) {
                        user.setPerson(optionalPerson.get());
                    }
                    Optional<Card> optionalCard = cardRepository.findById(user.getCardId());
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
        return cardConsumeRepository.findByCardNo(cardNo,pageable);
    }

    public Page<CardConsume> findByUserPhone(String userPhone, Pageable pageable){
        return cardConsumeRepository.findByUserPhone(userPhone,pageable);
    }

    public CardConsume add(CardConsume cardConsume) {
        CardConsume responseCardConsume = null;
        //根据cardNo获取user信息
        User user = userRepository.findByUserPhone(cardConsume.getUserPhone());
        if(user!=null){
            //保存消费记录
            cardConsume.setCardId(user.getCardId());
            cardConsume.setCardNo(cardConsume.getCardNo());
            cardConsume.setShopId(user.getShopId());
            cardConsume.setUserId(user.getId());
            cardConsume.setUserPhone(user.getUserPhone());
            cardConsume.setPayFee(cardConsume.getPayFee());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(new Date());
            cardConsume.setCreateTime(createTime);
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
