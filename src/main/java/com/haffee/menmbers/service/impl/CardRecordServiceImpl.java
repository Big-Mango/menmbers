package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardRecord;
import com.haffee.menmbers.repository.CardRecordRepository;
import com.haffee.menmbers.service.CardRecordService;
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
public class CardRecordServiceImpl implements CardRecordService {

    @Resource
    private CardRecordRepository cardRecordRepository;

    public Page<CardRecord> findAll(Pageable pageable) {
        return cardRecordRepository.findAll(pageable);
    }

    public Page<CardRecord> findByCardNo(String cardNo,Pageable pageable) {
        return cardRecordRepository.findByCardNo(cardNo,pageable);
    }

    public CardRecord add(CardRecord cardRecord) {
        if (cardRecordRepository.exists(Example.of(cardRecord))) {
            return null;
        } else {
            return cardRecordRepository.save(cardRecord);
        }
    }
}
