package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.Shop;

/**
 * create by jacktong
 * date 2018/8/4 下午3:09
 **/

public interface ShopService {

    int updateShopInfo(AdminUser a_user, Shop shop) throws Exception;

    void addShop(AdminUser a_user,Shop shop) throws Exception;

    void deleteShop(int shop_id,int a_user_id) throws Exception;
}
