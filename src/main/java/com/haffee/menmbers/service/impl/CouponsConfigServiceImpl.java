package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CouponsConfig;
import com.haffee.menmbers.repository.CouponsConfigRepository;
import com.haffee.menmbers.service.CouponsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * create by jacktong
 * date 2018/9/28 下午8:13
 **/

@Service
@Transactional
public class CouponsConfigServiceImpl implements CouponsConfigService {

    @Autowired
    private CouponsConfigRepository couponsConfigRepository;

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
        return couponsConfigRepository.findAllByShopId(shop_id);
    }
}
