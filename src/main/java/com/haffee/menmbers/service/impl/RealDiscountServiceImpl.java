package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.RealDiscountConfig;
import com.haffee.menmbers.repository.RealDiscountRepository;
import com.haffee.menmbers.service.RealDiscountService;
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

    @Override
    public List<RealDiscountConfig> findAllByShop(String shop_id) {
        return realDiscountRepository.findAllByShopId(shop_id);
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
