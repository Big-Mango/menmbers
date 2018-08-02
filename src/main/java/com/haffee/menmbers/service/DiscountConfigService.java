package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.DiscountConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
* @Description:    java类作用描述
* @Author:         liujia
* @CreateDate:     2018/7/29 9:55
* @Version:        1.0
*/
public interface DiscountConfigService {
    Page<DiscountConfig> findAll(Pageable pageable);
    DiscountConfig add(DiscountConfig discountConfigiscountConfig);
    DiscountConfig update(DiscountConfig discountConfig);
    void delete(DiscountConfig discountConfig);
}
