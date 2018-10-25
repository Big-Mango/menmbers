package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private String wechatAccCode; //获取token 所需Code
    private String access_token; //微信授权凭证
    private String paymentWay; //支付方式 多个字符串逗号拼接 1:手机验证，2：指纹，3：人脸，4：声波，5：其他
    private int status = 1; //状态 1：正常 0 冻结 -1
    private String remark; //备注
    private int personId = 0 ;
    private String createTime;//创建时间
    private Date lastLoginTime;
    private String loginKey; //登录时候标识
    @Transient
    private Person person;
    @Transient
    private List<Card> card_list;//不区分店铺的情况
    @Transient
    private Card card;//此处用于存储固定店铺下的用户的唯一的会员卡信息
    @Transient
    private int jifen = 0; //平台积分
    @Transient
    private List<Coupons> coupons_list;
    @Transient
    private int discount_use_time_month;

}
