package com.haffee.menmbers.utils;

import com.haffee.menmbers.entity.SmsTemplate;
import com.haffee.menmbers.entity.SysConfig;
import com.haffee.menmbers.repository.SmsTemplateRespository;
import com.haffee.menmbers.repository.SysConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * create by jacktong
 * date 2018/9/1 下午12:51
 **/

public class ConfigUtils {

    @Autowired
    private static SysConfigRepository sysConfigRepository;

    @Autowired
    private static SmsTemplateRespository smsTemplateRespository;

    private static String wechat_app_id;

    private static String wechat_secret;

    private static String wechat_pay_key;

    private static String wechat_pay_mch_id;

    private static String wechat_notice_url;

    private static String admin_account_add;

    private static String shop_account_add;

    private static String person_account_add;

    private static String person_recharge;//会员充值

    private static String person_consume;//会员消费

    private static String check_code;

    private static String use_code;

    private static String sms_user_id;

    private static String sms_user_pwd;

    public ConfigUtils() {
        wechat_app_id = getSysConfig(Constant.WECHAT_APP_ID);
        wechat_secret = getSysConfig(Constant.WECHAT_SECRET);
        wechat_pay_key = getSysConfig(Constant.WECHAT_PAY_KEY);
        wechat_pay_mch_id = getSysConfig(Constant.WECHAT_PAY_MCH_ID);
        wechat_notice_url = getSysConfig(Constant.NOTICE_URL);
        admin_account_add = getSmsTemplate(Constant.SMS_ADMIN_ACCOUNT_ADD);
        shop_account_add = getSmsTemplate(Constant.SMS_SHOP_ACCOUNT_ADD);
        person_account_add = getSmsTemplate(Constant.SMS_PERSON_ACCOUNT_ADD);
        person_recharge = getSmsTemplate(Constant.SMS_PERSON_RECHARGE);
        person_consume = getSmsTemplate(Constant.SMS_PERSON_CONSUME);
        check_code = getSmsTemplate(Constant.SMS_CHECK_CODE);
        use_code = getSmsTemplate(Constant.SMS_USE_CODE);
        sms_user_id = getSysConfig(Constant.SMS_USER_ID);
        sms_user_pwd = getSysConfig(Constant.SMS_PSW);
    }

    /**
     * 加载系统配置
     *
     * @param param
     * @return
     */
    public static String getSysConfig(String param) {
        SysConfig sc = sysConfigRepository.selectByKeyParam(param);
        if (null == sc) {
            return null;
        } else {
            return sc.getParamValue();
        }
    }

    public static String getSmsTemplate(String code) {
        SmsTemplate st = smsTemplateRespository.findOneByCode(code);
        if (null == st) {
            return null;
        } else {
            return st.getTemplate_content();
        }
    }


    public static String getWechat_app_id() {
        return wechat_app_id;
    }

    public static String getWechat_secret() {
        return wechat_secret;
    }

    public static String getAdmin_account_add() {
        return admin_account_add;
    }

    public static String getShop_account_add() {
        return shop_account_add;
    }

    public static String getPerson_account_add() {
        return person_account_add;
    }

    public static String getPerson_recharge() {
        return person_recharge;
    }

    public static String getPerson_consume() {
        return person_consume;
    }

    public static String getCheck_code() {
        return check_code;
    }

    public static String getUse_code() {
        return use_code;
    }

    public static String getSms_user_id() {
        return sms_user_id;
    }

    public static String getSms_user_pwd() {
        return sms_user_pwd;
    }

    public static String getWechat_pay_key() {
        return wechat_pay_key;
    }

    public static String getWechat_pay_mch_id() {
        return wechat_pay_mch_id;
    }

    public static String getWechat_notice_url() {
        return wechat_notice_url;
    }
}
