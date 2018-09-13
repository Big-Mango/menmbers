package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.CardRecharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
* @Description:    java类作用描述
* @Author:         liujia
* @CreateDate:     2018/7/29 9:55
* @Version:        1.0
*/
public interface CardRechargeService {
    Page<CardRecharge> findAllByShopId(Pageable pageable,int shopId);
    Page<CardRecharge> findByCardNo(String cardNo,Pageable pageable);
    Page<CardRecharge> findByUserPhone(String userPhone,Pageable pageable);
    CardRecharge add(CardRecharge cardRecharge);
    Optional<CardRecharge> findById(int id);
    CardRecharge findOneByOrderNo(String order_no);
    CardRecharge save(CardRecharge cr);
}
