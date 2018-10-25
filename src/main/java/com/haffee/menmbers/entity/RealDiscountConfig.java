package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * create by jacktong
 * date 2018/10/9 下午7:23
 * 会员消费打折配置
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class RealDiscountConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int discountValue; //折扣百分比 98 代表0.98折
    private int shopId;
    private int type = 0;//0:长期有效，1：限时有效
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private int if_over = 0;//是否与其他优惠共享 0：否，1：是
    private int status = 1;//状态：0：未生效，1：生效
    private int man = 0; //最低消费触发
    private int use_time_month = 0; //每月使用次数
    private Date createTime;

}
