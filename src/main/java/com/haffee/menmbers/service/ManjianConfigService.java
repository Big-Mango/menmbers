package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.ManjianConfig;

import java.util.List;

/**
 * create by jacktong
 * date 2018/10/9 下午7:50
 **/

public interface ManjianConfigService {

    List<ManjianConfig> findAllByShop(String shop_id);

    ManjianConfig findOneEnable(String shop_id);

    void deleteConfig(ManjianConfig config);

    void changeStatus(int config_id,int status);

    void addConfig(ManjianConfig config);


}
