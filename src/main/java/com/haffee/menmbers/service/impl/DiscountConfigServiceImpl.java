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
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public Page<DiscountConfig> findAll(Pageable pageable,int shopId){
        return discountConfigRepository.findAll(pageable,shopId);
    }
    public DiscountConfig add(DiscountConfig discountConfig){
        if(discountConfigRepository.exists(Example.of(discountConfig))){
            return null;
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(new Date());
            discountConfig.setCreateTime(createTime);
            return discountConfigRepository.save(discountConfig);
        }
    }
    public DiscountConfig update(DiscountConfig discountConfig){
        return discountConfigRepository.save(discountConfig);
    }
    public void delete(int id){
        discountConfigRepository.deleteById(id);
    }
}