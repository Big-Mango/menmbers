package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.service.CustomerService;
import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.HttpClientUtils;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by jacktong
 * date 2018/9/1 下午12:58
 **/

@RestController
@RequestMapping("/2c")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 获取关注公众号用户Token
     * @param acc_code
     * @return
     */
    @PostMapping("/wechat/getToken")
    public ResponseMessage getAccessToken(String acc_code) {
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ConfigUtils.getWechat_app_id() + "&secret=" + ConfigUtils.getWechat_secret() + "&code=" + acc_code + "&grant_type=authorization_code";
            String result = HttpClientUtils.get(url);
            return ResponseMessage.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }


    /**
     * 验证个人手机号码,并绑定微信
     * @param openid
     * @param phone_no
     * @return
     */
    @PostMapping("/wechat/checkPhone")
    public ResponseMessage checkPhone(String phone_no,String openid,String access_token){
        try {
            User user = customerService.checkUserPhone(phone_no,openid,access_token);
            return ResponseMessage.success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }


}
