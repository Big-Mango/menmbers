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
    private String userPhone; //手机号
    private String password;
    private String wechatId; //微信唯一标识
    private String wechatNickname; //微信昵称
    private String wechatIcon; //微信头像
    private String paymentWay; //支付方式 多个字符串逗号拼接 1:手机验证，2：指纹，3：人脸，4：声波，5：其他
    private float balance = 0; //余额
    private int status = 1; //状态 1：正常 -1 冻结
    private String remark; //备注
    private int personId = 0 ;
    private int cardId = 0; //会员卡ID
    private int shopId = 0;//商户id
    private Date lastLoginTime;
    private String loginKey; //登录时候标识
    private Person person;
    private Card card;
}
