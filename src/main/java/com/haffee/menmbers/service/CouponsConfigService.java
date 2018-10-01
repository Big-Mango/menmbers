package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.CouponsConfig;

import java.util.List;

/**
 * create by jacktong
 * date 2018/9/28 下午8:12
 **/

public interface CouponsConfigService {

    CouponsConfig add_config(CouponsConfig config);

    void delete_config(CouponsConfig config);

    void changeStatus(int config_id,int status);

    List<CouponsConfig> findAll(int shop_id);




}
