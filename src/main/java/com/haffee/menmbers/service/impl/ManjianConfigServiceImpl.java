package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.ManjianConfig;
import com.haffee.menmbers.repository.ManjianConfigRepository;
import com.haffee.menmbers.service.ManjianConfigService;
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

    @Override
    public List<ManjianConfig> findAllByShop(String shop_id) {
        return manjianConfigRepository.findAllByShopId(shop_id);
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
