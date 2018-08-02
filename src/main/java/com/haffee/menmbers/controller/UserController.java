package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 后台管理登录
     * @param userPhone
     * @param password
     * @param type
     * @return
     */
    @PostMapping("/admin/login")
    public ResponseMessage doLoginForA(String userPhone,String password,String type){
        try {
            AdminUser a_user = userService.doLoginForAdmin(userPhone,password,type);
            if(null!=a_user){
                return ResponseMessage.success(a_user);
            }else{
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 消费者登录
     * @param userPhone
     * @param password
     * @return
     */
    @PostMapping("/customer/login")
    public ResponseMessage doLoginForC(String userPhone,String password){
        try {
            User c_user = userService.doLoginForCustomer(userPhone,password);
            if(null!=c_user){
                return ResponseMessage.success(c_user);
            }else{
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 后台用户注销
     * @param userPhone
     * @param type
     * @return
     */
    @PostMapping("/admin/logout")
    public ResponseMessage doLogoutForA(String userPhone,String type){
        try {
            userService.doLogoutForAdmin(userPhone,type);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }

    }

    /**
     * 消费者注销
     * @param userPhone
     * @return
     */
    @PostMapping("/customer/logout")
    public ResponseMessage doLogoutForC(String userPhone){
        try {
            userService.doLogoutForCustomer(userPhone);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 新增系统管理员
     * @param a_user
     * @return
     */
    @PostMapping("/admin/add")
    public ResponseMessage addAdminUser(AdminUser a_user){
        try {
            userService.doAddAdmin(a_user.getUserPhone());
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }

    }
}
