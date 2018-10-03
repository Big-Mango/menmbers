package com.haffee.menmbers.utils;

/**
 * create by jacktong
 * date 2018/10/3 上午10:55
 **/

public class WechatNoticeUtil {

    /**
     * 发送公众号通知
     * @param access_token
     * @param jsonstr
     * @return
     */
    public static String sendNotice(String access_token,String jsonstr){
        try {
            String result = HttpClientUtils.doPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,jsonstr);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
