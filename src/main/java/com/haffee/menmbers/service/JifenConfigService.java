package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.JifenConfig;

import java.util.List;

/**
 * create by jacktong
 * date 2018/10/22 下午7:12
 **/

public interface JifenConfigService {
    List<JifenConfig> findAllByShop(String shop_id);

    JifenConfig findOneEnable(String shop_id);

    void deleteConfig(JifenConfig config);

    void changeStatus(int config_id,int status);

    void addConfig(JifenConfig config);
}
