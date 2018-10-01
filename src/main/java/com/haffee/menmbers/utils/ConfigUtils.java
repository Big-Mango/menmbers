package com.haffee.menmbers.utils;

import com.haffee.menmbers.entity.SmsTemplate;
import com.haffee.menmbers.entity.SysConfig;
import com.haffee.menmbers.repository.SmsTemplateRespository;
import com.haffee.menmbers.repository.SysConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by jacktong
 * date 2018/9/1 下午12:51
 **/
public class ConfigUtils {
    private static SysConfigRepository sysConfigRepository;
//    private static SmsTemplateRespository smsTemplateRespository;
    /**
     * 加载系统配置
     *
     * @param param
     * @return
     */
    public static String getSysConfig(String param) {
        sysConfigRepository = SpringUtil.getBean(SysConfigRepository.class);
        SysConfig sc = sysConfigRepository.selectByKeyParam(param);
        if (null == sc) {
            return null;
        } else {
            return sc.getParamValue();
        }
    }

//    public static String getSmsTemplate(String code) {
//        smsTemplateRespository = SpringUtil.getBean(SmsTemplateRespository.class);
//        SmsTemplate st = smsTemplateRespository.findOneByCode(code);
//        if (null == st) {
//            return null;
//        } else {
//            return st.getTemplate_content();
//        }
//    }

    public static String getWechat_app_id() {
        return getSysConfig(Constant.WECHAT_APP_ID);
    }

    public static String getWechat_secret() {
        return getSysConfig(Constant.WECHAT_SECRET);
    }

    public static String getAdmin_account_add() {
        return getSysConfig(Constant.SMS_ADMIN_ACCOUNT_ADD);
    }

    public static String getShop_account_add() {
        return getSysConfig(Constant.SMS_SHOP_ACCOUNT_ADD);
    }

    public static String getPerson_account_add() {
        return getSysConfig(Constant.SMS_PERSON_ACCOUNT_ADD);
    }

    public static String getPerson_recharge() {
        return getSysConfig(Constant.SMS_PERSON_RECHARGE);
    }

    public static String getPerson_consume() {
        return getSysConfig(Constant.SMS_PERSON_CONSUME);
    }

    public static String getCheck_code() {
        return getSysConfig(Constant.SMS_CHECK_CODE);
    }

    public static String getUse_code() {
        return getSysConfig(Constant.SMS_USE_CODE);
    }

    public static String getSms_user_id() {
        return getSysConfig(Constant.SMS_USER_ID);
    }

    public static String getSms_user_pwd() {
        return getSysConfig(Constant.SMS_PSW);
    }

    public static String getWechat_pay_key() {
        return getSysConfig(Constant.WECHAT_PAY_KEY);
    }

    public static String getWechat_pay_mch_id() {
        return getSysConfig(Constant.WECHAT_PAY_MCH_ID);
    }

    public static String getWechat_notice_url() {
        return getSysConfig(Constant.NOTICE_URL);
    }
}
