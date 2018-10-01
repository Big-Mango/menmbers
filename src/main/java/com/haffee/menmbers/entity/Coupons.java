package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * create by jacktong
 * date 2018/9/28 下午8:05
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class Coupons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int shopId;
    private int type=0;// 0:长期有效，1：限时有效
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    private float coupon_value = 0;
    private float min_use_fee=0; //大于0有效 最低使用金额
    private int if_over=0;//是否允许叠加 0：否，1：是
    private String valid_code; //校验码
    private int sentStatus=0;//0:未领取，1：已领取
    private int useStaus=0;//0:未使用，1：已使用，-1：已过期
    private Date createTime;
    @Transient
    private Shop shop;
}
