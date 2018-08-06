package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private int type = 0; //2:店铺，9：系统管理员
    private int ifChain = 0; //是否连锁 1：是 0：否
    private int ifBoss = 0; //是否总店
    private int parentUserId = 0; //上级店铺ID；
    private int shopId = 0;
    private int status = 1; //用户状态 1：正常 -1 冻结
    private String remark;
    private Date lastLoginTime;//最近登录时间
    private String loginKey; //登录标识
    @Transient
    private Shop shop; //关联店铺信息


}
