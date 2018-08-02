package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.User;

/**
 * create by jacktong
 * date 2018/7/15 下午4:44
 **/

public interface UserService {

    AdminUser doLoginForAdmin(String userPhone, String password, String type) throws Exception;

    User doLoginForCustomer(String userPhone,String password) throws Exception;

    boolean doLogoutForAdmin(String userPhone,String type) throws Exception;

    boolean doLogoutForCustomer(String userPhone) throws Exception;

    int doAddAdmin(String userPhone) throws Exception;

    int changeUserStatus(String id,int status) throws Exception;

    int changeAdminUserStatus(String id,int status) throws Exception;

}
