package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * create by jacktong
 * date 2018/7/15 下午4:32
 * 会员系统用户
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private String user_phone; //手机号
    private String password;
    private String wechat_id; //微信唯一标识
    private String wechat_nickname; //微信昵称
    private String wechat_icon; //微信头像
    private String payment_way; //支付方式 多个字符串逗号拼接 1:手机验证，2：指纹，3：人脸，4：声波，5：其他
    private float balance; //余额
    private int status; //状态 1：正常 -1 冻结
    private String remark; //备注
    private int person_id;
    private int card_id; //会员卡ID
    private Date last_login_time;
    private String login_key; //登录时候标识



}
