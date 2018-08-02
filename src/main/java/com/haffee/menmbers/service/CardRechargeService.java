package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.CardRecharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
* @Description:    java类作用描述
* @Author:         liujia
* @CreateDate:     2018/7/29 9:55
* @Version:        1.0
*/
public interface CardRechargeService {
    Page<CardRecharge> findAll(Pageable pageable);
    CardRecharge findByCardNo(String cardNo);
    CardRecharge add(CardRecharge cardRecharge);
}
