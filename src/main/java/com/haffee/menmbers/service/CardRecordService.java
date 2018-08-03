package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.CardRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
* @Description:    java类作用描述
* @Author:         liujia
* @CreateDate:     2018/7/29 9:55
* @Version:        1.0
*/
public interface CardRecordService {
    Page<CardRecord> findAll(Pageable pageable);
    Page<CardRecord> findByCardNo(String cardNo,Pageable pageable);
    CardRecord add(CardRecord cardRecord);
}
