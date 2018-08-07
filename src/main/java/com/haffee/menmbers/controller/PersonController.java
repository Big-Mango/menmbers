package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.Person;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.service.PersonService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/findOne")
    public ResponseMessage findOnePerson(String user_id){
        try {
            User user = personService.findOneUser(user_id);
            return ResponseMessage.success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 更新个人信息
     * @param user
     * @param user_id
     * @param person
     * @param person_id
     * @return
     */
    @PostMapping("/update")
    public ResponseMessage updateUser(@RequestBody User user, int user_id, @RequestBody Person person, int person_id){
        try {
            user.setId(user_id);
            person.setId(person_id);
            personService.updateUserInfo(user,person);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }



}
