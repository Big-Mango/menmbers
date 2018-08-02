package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.DiscountConfig;
import com.haffee.menmbers.repository.CardRepository;
import com.haffee.menmbers.repository.DiscountConfigRepository;
import com.haffee.menmbers.service.CardService;
import com.haffee.menmbers.service.DiscountConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
* @Description:    java类作用描述
* @Author:         liujia
* @CreateDate:     2018/7/29 10:25
* @Version:        1.0
*/
@Service
@Transactional
public class DiscountConfigServiceImpl implements DiscountConfigService {

    @Resource
    private DiscountConfigRepository discountConfigRepository;

    public Page<DiscountConfig> findAll(Pageable pageable){
        return discountConfigRepository.findAll(pageable);
    }
    public DiscountConfig add(DiscountConfig discountConfig){
        if(discountConfigRepository.exists(Example.of(discountConfig))){
            return null;
        }else{
            return discountConfigRepository.save(discountConfig);
        }
    }
    public DiscountConfig update(DiscountConfig discountConfig){
        return discountConfigRepository.save(discountConfig);
    }
    public void delete(DiscountConfig discountConfig){
        discountConfigRepository.delete(discountConfig);
    }
}