package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.service.PersonService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by jacktong
 * date 2018/8/4 下午4:35
 * 个人公众号相关功能
 **/

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * 查询个人信息
     * @param user_id
     * @return
     */
    @GetMapping("/findOnePerson")
    public ResponseMessage findOnePerson(String user_id){
        try {
            User user = personService.findOneUser(user_id);
            return ResponseMessage.success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }



}
