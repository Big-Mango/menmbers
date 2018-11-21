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
 * date 2018/9/28 下午7:55
 **/


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class CouponsConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int shopId;
    private int type=0;// 0:长期有效，1：限时有效
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    private float coupon_value = 0;
    private float min_use_fee=0; //大于0有效 最低使用金额
    private int if_over=0;//是否允许叠加 0：否，1：是
    private Date createTime;
    private int first_user_give=0;//0:否，1：是 首次开卡用户送券
    private int status = 1;//1:生效，0：不生效
    private String cardType; //字符串，逗号分隔，试用卡类型
    @Transient
    private String card_type_name;

}
