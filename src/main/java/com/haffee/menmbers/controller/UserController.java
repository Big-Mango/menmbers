package com.haffee.menmbers.controller;

import com.haffee.menmbers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by jacktong
 * date 2018/7/15 下午4:28
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
}
