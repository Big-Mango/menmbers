package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.SysCode;
import com.haffee.menmbers.repository.CardRepository;
import com.haffee.menmbers.repository.SysCodeRepository;
import com.haffee.menmbers.service.BaseService;
import com.haffee.menmbers.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
}
