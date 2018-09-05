package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.SysCode;
import com.haffee.menmbers.service.BaseService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by jacktong
 * date 2018/7/26 下午6:53
 **/

@RestController
@RequestMapping("/base")
public class BaseController {

    @Autowired
    private BaseService baseService;

    /**
     * 根据Code 查询
     * @param code
     * @return
     */
    @GetMapping("/findByCode")
    public ResponseMessage findByCode(String code){
        try {
            List<SysCode> code_list = baseService.selectByCode(code);
            return ResponseMessage.success(code_list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @PostMapping("/sms/sendCheckCode")
    public ResponseMessage sendCheckCode(String phone){
        try {
            baseService.sendCheckCode(phone);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 校验验证码
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/sms/checkCode")
    public ResponseMessage doCheckCode(String phone,String code){
        try {
            boolean isSuccess = baseService.doCheckCode(phone,code);
            if(isSuccess){
                return ResponseMessage.success();
            }else{
                return ResponseMessage.error();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

}
