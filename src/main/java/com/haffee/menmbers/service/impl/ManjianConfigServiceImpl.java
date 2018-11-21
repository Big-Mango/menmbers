package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardType;
import com.haffee.menmbers.entity.ManjianConfig;
import com.haffee.menmbers.entity.RealDiscountConfig;
import com.haffee.menmbers.repository.CardTypeRepository;
import com.haffee.menmbers.repository.ManjianConfigRepository;
import com.haffee.menmbers.service.ManjianConfigService;
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
public class ManjianConfigServiceImpl implements ManjianConfigService {

    @Autowired
    private ManjianConfigRepository manjianConfigRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @Override
    public List<ManjianConfig> findAllByShop(String shop_id) {

        List<ManjianConfig> list =  manjianConfigRepository.findAllByShopId(shop_id);
        if(list.size()>0){
            for (ManjianConfig c:list) {
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
    public ManjianConfig findOneEnable(String shop_id) {
        return manjianConfigRepository.findOneEnableByShopId(shop_id);
    }

    @Override
    public void deleteConfig(ManjianConfig config) {
        manjianConfigRepository.delete(config);
    }

    @Override
    public void changeStatus(int config_id, int status) {
        Optional<ManjianConfig> o = manjianConfigRepository.findById(config_id);
        if(o.isPresent()){
            o.get().setStatus(status);
            manjianConfigRepository.save(o.get());
        }
    }

    @Override
    public void addConfig(ManjianConfig config) {
        config.setCreateTime(new Date());
        manjianConfigRepository.save(config);
    }
}
