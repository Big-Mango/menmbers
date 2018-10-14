package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.RealDiscountConfig;

import java.util.List;

/**
 * create by jacktong
 * date 2018/10/9 下午7:50
 **/

public interface RealDiscountService {

    List<RealDiscountConfig> findAllByShop(String shop_id);

    RealDiscountConfig findOneEnable(String shop_id);

    void deleteConfig(RealDiscountConfig config);

    void addConfig(RealDiscountConfig config);

    void changeStatus(int config_id,int status);
}
