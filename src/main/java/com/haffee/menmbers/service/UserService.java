package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.User;

/**
 * create by jacktong
 * date 2018/7/15 下午4:44
 **/

public interface UserService {

    AdminUser doLoginForAdmin(String user_phone, String password, String type) throws Exception;

    User doLoginForCustomer(String user_phone,String password) throws Exception;

    boolean doLogoutForAdmin(String user_phone,String type) throws Exception;

    boolean doLogoutForCustomer(String user_phone) throws Exception;

    int doAddAdmin(String user_phone) throws Exception;

}
