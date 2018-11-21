package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardType;
import com.haffee.menmbers.entity.JifenConfig;
import com.haffee.menmbers.entity.RealDiscountConfig;
import com.haffee.menmbers.repository.CardTypeRepository;
import com.haffee.menmbers.repository.JifenConfigRepository;
import com.haffee.menmbers.service.JifenConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * create by jacktong
 * date 2018/10/22 下午7:13
 **/

@Service
@Transactional
public class JifenConfigServiceImpl implements JifenConfigService {

    @Autowired
    private JifenConfigRepository jifenConfigRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @Override
    public List<JifenConfig> findAllByShop(String shop_id) {


        List<JifenConfig> list =  jifenConfigRepository.findAllByShopId(shop_id);
        if(list.size()>0){
            for (JifenConfig c:list) {
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
    public JifenConfig findOneEnable(String shop_id) {
        return jifenConfigRepository.findOneEnableByShopId(shop_id);
    }

    @Override
    public void deleteConfig(JifenConfig config) {
        jifenConfigRepository.delete(config);
    }

    @Override
    public void changeStatus(int config_id, int status) {
        Optional<JifenConfig> o = jifenConfigRepository.findById(config_id);
        if(o.isPresent()){
            o.get().setStatus(status);
            jifenConfigRepository.save(o.get());
        }
    }

    @Override
    public void addConfig(JifenConfig config) {
        config.setCreateTime(new Date());
        jifenConfigRepository.save(config);
    }
}
