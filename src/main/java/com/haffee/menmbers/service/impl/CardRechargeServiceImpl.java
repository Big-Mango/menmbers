package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardRecharge;
import com.haffee.menmbers.repository.CardRechargeRepository;
import com.haffee.menmbers.service.CardRechargeService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    public Page<CardRecharge> findAll(Pageable pageable) {
        return cardRechargeRepository.findAll(pageable);
    }

    public CardRecharge findByCardNo(String cardNo) {
        return cardRechargeRepository.findByCardNo(cardNo);
    }

    public CardRecharge add(CardRecharge cardRecharge) {
        if (cardRechargeRepository.exists(Example.of(cardRecharge))) {
            return null;
        } else {
            return cardRechargeRepository.save(cardRecharge);
        }
    }
}
