package com.haffee.menmbers.utils.wxpay;

import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.HttpClientUtils;
import net.sf.json.JSONObject;

/**
 * create by jacktong
 * date 2018/10/3 下午12:41
 **/

public class WXAccessToken {

    public static String getAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ConfigUtils.getWechat_app_id()+"&secret="+ConfigUtils.getWechat_secret();
        String result = HttpClientUtils.get(url);
        JSONObject jsStr = JSONObject.fromObject(result);
        String access_token = jsStr.get("access_token")+"";
        return access_token;
    }

}
