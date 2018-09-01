package com.haffee.menmbers.utils;

import com.haffee.menmbers.entity.SysConfig;
import com.haffee.menmbers.repository.SysConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * create by jacktong
 * date 2018/9/1 下午12:51
 **/

public class ConfigUtils {

    @Autowired
    private static SysConfigRepository sysConfigRepository;

    private static String wechat_app_id;

    private static String wechat_secret;

    public ConfigUtils() {
        wechat_app_id = getSysConfig(Constant.WECHAT_APP_ID);
        wechat_secret = getSysConfig(Constant.WECHAT_SECRET);
    }

    /**
     * 加载系统配置
     * @param param
     * @return
     */
    public static String getSysConfig(String param){
        SysConfig sc = sysConfigRepository.selectByKeyParam(param);
        if(null==sc){
            return null;
        }else{
            return sc.getParamValue();
        }
    }

    public static String getWechat_app_id() {
        return wechat_app_id;
    }

    public static String getWechat_secret() {
        return wechat_secret;
    }
}
