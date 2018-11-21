package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.CardType;
import com.haffee.menmbers.entity.DiscountConfig;
import com.haffee.menmbers.repository.CardRepository;
import com.haffee.menmbers.repository.CardTypeRepository;
import com.haffee.menmbers.repository.DiscountConfigRepository;
import com.haffee.menmbers.service.CardService;
import com.haffee.menmbers.service.DiscountConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private CardTypeRepository cardTypeRepository;

    public Page<DiscountConfig> findAll(Pageable pageable,int shopId){

        Page<DiscountConfig> page = discountConfigRepository.findAll(pageable,shopId);
        List<DiscountConfig> list =  page.getContent();
        if(list.size()>0){
            for (DiscountConfig c:list) {
                if(StringUtils.isNotEmpty(c.getCardType())){
                    String [] array = c.getCardType().split(",");
                    StringBuffer buffer = new StringBuffer();
                    for (String s:array) {
                        Optional<CardType> o = cardTypeRepository.findById(Integer.valueOf(s));
                        if(o.isPresent()){
                            buffer.append(o.get().getCard_name()+"，");
                        }
                    }
                    c.setCard_type_name(buffer.substring(0,buffer.length()-1));
                }
            }
        }
        return page;
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