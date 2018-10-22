package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.JifenConfig;
import com.haffee.menmbers.repository.JifenConfigRepository;
import com.haffee.menmbers.service.JifenConfigService;
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

    @Override
    public List<JifenConfig> findAllByShop(String shop_id) {
        return jifenConfigRepository.findAllByShopId(shop_id);
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
