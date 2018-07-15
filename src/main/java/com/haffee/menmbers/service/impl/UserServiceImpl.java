package com.haffee.menmbers.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.haffee.menmbers.repository.UserRepositroy;
import com.haffee.menmbers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * create by jacktong
 * date 2018/7/15 下午4:44
 **/

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositroy userRepositroy;
}
