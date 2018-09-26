package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.SysCode;
import com.haffee.menmbers.service.BaseService;
import com.haffee.menmbers.utils.RandomValidateCodeUtil;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                return ResponseMessage.errorWithMsg("验证码校验失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 生成验证码
     */
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("sessionid:"+request.getSession().getId());
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
