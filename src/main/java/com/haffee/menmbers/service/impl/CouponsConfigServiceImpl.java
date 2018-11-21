package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardType;
import com.haffee.menmbers.entity.CouponsConfig;
import com.haffee.menmbers.entity.DiscountConfig;
import com.haffee.menmbers.repository.CardTypeRepository;
import com.haffee.menmbers.repository.CouponsConfigRepository;
import com.haffee.menmbers.service.CouponsConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * create by jacktong
 * date 2018/9/28 下午8:13
 **/

@Service
@Transactional
public class CouponsConfigServiceImpl implements CouponsConfigService {

    @Autowired
    private CouponsConfigRepository couponsConfigRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;

    /**
     * 新增优惠券配置方案
     * @param config
     * @return
     */
    @Override
    public CouponsConfig add_config(CouponsConfig config) {
        config.setStatus(1);
        config.setCreateTime(new Date());
        return couponsConfigRepository.save(config);
    }

    /**
     * 删除
     * @param config
     */
    @Override
    public void delete_config(CouponsConfig config) {
        couponsConfigRepository.delete(config);
    }

    /**
     * 修改状态
     * @param config_id
     * @param status
     */
    @Override
    public void changeStatus(int config_id, int status) {
        couponsConfigRepository.changeStatus(config_id,status);
    }

    /**
     * 查询所有优惠方案配置
     * @param shop_id
     * @return
     */
    @Override
    public List<CouponsConfig> findAll(int shop_id) {

        List<CouponsConfig> list =  couponsConfigRepository.findAllByShopId(shop_id);
        if(list.size()>0){
            for (CouponsConfig c:list) {
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
}
