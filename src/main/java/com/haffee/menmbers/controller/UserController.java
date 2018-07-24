package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.ResponseMessage;
import com.haffee.menmbers.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * create by jacktong
 * date 2018/7/15 下午4:28
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 后台管理登录
     * @param user_id
     * @param password
     * @param type
     * @return
     */
    @PostMapping("/adminLogin")
    public ResponseMessage doLogin(String user_id,String password,String type){
        try {
            AdminUser a_user = userService.dologinForAdmin(user_id,password,type);
            if(null!=a_user){
                String login_key =UuidUtils.getUUID32();
                a_user.setLogin_key(login_key);
                Date now = new Date();
                a_user.setLast_login_time(now);
                userService.updateAdminUser(a_user);
                return ResponseMessage.success(a_user);
            }else{
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }

    }
}
