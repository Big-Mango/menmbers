package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.CardConsume;
import com.haffee.menmbers.entity.CardRecharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
* @Description:    java类作用描述
* @Author:         liujia
* @CreateDate:     2018/7/29 9:55
* @Version:        1.0
*/
public interface CardConsumeService {
    Page<CardConsume> findAllByShopId(Pageable pageable,int shopId);
    Page<CardConsume> findByCardNo(String cardNo, Pageable pageable);
    Page<CardConsume> findByUserPhone(String userPhone, Pageable pageable);
    CardConsume add(CardConsume cardConsume);
    Optional<CardConsume> findById(int id);
}
