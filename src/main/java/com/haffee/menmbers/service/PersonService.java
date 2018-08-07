package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.Person;
import com.haffee.menmbers.entity.User;

/**
 * create by jacktong
 * date 2018/8/4 下午4:36
 * 个人公众号相关功能
 **/

public interface PersonService {

    User findOneUser(String id) throws Exception;

    void updateUserInfo(User user,Person person) throws Exception;

}
