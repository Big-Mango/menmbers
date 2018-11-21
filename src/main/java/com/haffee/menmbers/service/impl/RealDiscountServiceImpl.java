package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardType;
import com.haffee.menmbers.entity.CouponsConfig;
import com.haffee.menmbers.entity.RealDiscountConfig;
import com.haffee.menmbers.repository.CardTypeRepository;
import com.haffee.menmbers.repository.RealDiscountRepository;
import com.haffee.menmbers.service.RealDiscountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * create by jacktong
 * date 2018/10/9 下午7:51
 **/

@Service
@Transactional
public class RealDiscountServiceImpl implements RealDiscountService {

    @Autowired
    private RealDiscountRepository realDiscountRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @Override
    public List<RealDiscountConfig> findAllByShop(String shop_id) {

        List<RealDiscountConfig> list =  realDiscountRepository.findAllByShopId(shop_id);
        if(list.size()>0){
            for (RealDiscountConfig c:list) {
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

        return list;
    }

    @Override
    public RealDiscountConfig findOneEnable(String shop_id) {
        return realDiscountRepository.findOneByShop(shop_id);
    }

    @Override
    public void deleteConfig(RealDiscountConfig config) {
        realDiscountRepository.delete(config);
    }

    @Override
    public void addConfig(RealDiscountConfig config) {
        config.setCreateTime(new Date());
        realDiscountRepository.save(config);
    }

    @Override
    public void changeStatus(int config_id, int status) {
        Optional<RealDiscountConfig> o = realDiscountRepository.findById(config_id);
        if(o.isPresent()){
            o.get().setStatus(status);
            realDiscountRepository.save(o.get());
        }
    }
}
