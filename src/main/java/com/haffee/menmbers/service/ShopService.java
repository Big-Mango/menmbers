package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.Shop;

import java.util.List;

/**
 * create by jacktong
 * date 2018/8/4 下午3:09
 **/

public interface ShopService {

    int updateShopInfo(AdminUser a_user, Shop shop) throws Exception;

    void addShop(AdminUser a_user,Shop shop) throws Exception;

    void deleteShop(int shop_id,int a_user_id) throws Exception;

    List<AdminUser>  findAllShop(String phone_or_name);

    AdminUser combineLogin(String vip_shop_id);

}
