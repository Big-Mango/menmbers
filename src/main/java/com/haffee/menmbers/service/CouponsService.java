package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.Coupons;

import java.util.List;

/**
 * create by jacktong
 * date 2018/9/28 下午9:14
 **/

public interface CouponsService {

    List<Coupons> findEnableCouponsByUserAndShop(int shop_id, int user_id,float order_fee);

    List<Coupons> findAllCouponsByUser(int user_id);

}
