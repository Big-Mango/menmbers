package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.AdminUser;

/**
 * create by jacktong
 * date 2018/7/15 下午4:44
 **/

public interface UserService {

    AdminUser dologinForAdmin(String user_id, String password, String type) throws Exception;

    boolean updateAdminUser(AdminUser a_user) throws Exception;
}
