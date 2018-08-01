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
 * date 2018/7/16 下午7:14
 * 系统管理账户
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userPhone; //手机号
    private String password;
    private int type; //2:店铺，9：系统管理员
    private int ifChain; //是否连锁 1：是 0：否
    private int ifBoss; //是否总店
    private int parentUserId; //上级店铺ID；
    private int shopId;
    private int status; //用户状态 1：正常 -1 冻结
    private String remark;
    private Date lastLoginTime;//最近登录时间
    private String loginKey; //登录标识


}
