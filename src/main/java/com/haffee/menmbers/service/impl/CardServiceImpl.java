package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.SysCode;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.repository.CardRepository;
import com.haffee.menmbers.repository.SysCodeRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.BaseService;
import com.haffee.menmbers.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
public class CardServiceImpl implements CardService {

    @Resource
    private CardRepository cardRepository;

    @Resource
    private UserRepository userRepository;

    public Page<Card> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    public Card findByCardNo(String cardNo) {
        return cardRepository.findByCardNo(cardNo);
    }

    public Optional<Card> findById(int id) {
        return cardRepository.findById(id);
    }

    public Card add(Card card) {
        if (cardRepository.exists(Example.of(card))) {
            return null;
        } else {
            return cardRepository.save(card);
        }
    }

    public Card update(Card card) {
        return cardRepository.save(card);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }

    public Card changeCardStatus(String cardNo,int cardStatus){
        Card card = cardRepository.findByCardNo(cardNo);
        if(card!=null){
            card.setCardStatus(cardStatus);
            return cardRepository.save(card);
        } else{
            return null;
        }
    }

    public void replace(String oldCardNo,String newCardNo){
        //将老卡置为 0：挂失
        Card oldCard = cardRepository.findByCardNo(oldCardNo);
        oldCard.setCardStatus(0);
        cardRepository.save(oldCard);

        //保存新卡信息,status为1：正常,shop为老卡的shopId
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(new Date());
        Card newCard = new Card();
        newCard.setCardNo(newCardNo);
        newCard.setCardStatus(1);
        newCard.setCardType(oldCard.getCardType());
        newCard.setCardCreateTime(createTime);
        newCard.setShopId(oldCard.getShopId());
        newCard.setUserId(oldCard.getUserId());
        newCard.setBalance(oldCard.getBalance());
        newCard.setJifen(oldCard.getJifen());
        cardRepository.save(newCard);
    }
}
