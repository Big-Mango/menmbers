package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.Person;
import com.haffee.menmbers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

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

    int changePasswordForAdminUser(String id,String password,int type) throws Exception;

    int changePasswordForUser(String id,String password) throws Exception;

    AdminUser findOneAdminUserForShop(String id) throws Exception;

    Page<AdminUser> findAdminUser(Pageable pageable,int type) throws Exception;

    Page<User> findAllUser(Pageable pageable) throws Exception;

    Page<User> findOneUserByUserPhone(Pageable pageable,String userPhone) throws Exception;

    User findOneUser(int userId) throws Exception;

    User add(Person person, Card card) throws Exception;

    User update(Person person, Card card, User user) throws Exception;

    AdminUser findAdminUser(String userPhone) throws Exception;

    void deleteAdmin(int id) throws Exception;
}
