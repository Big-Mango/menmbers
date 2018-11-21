package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * create by jacktong
 * date 2018/7/16 下午7:23
 * 优惠活动配置
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class DiscountConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private String name;//优惠名称
    private int shopId;
    private float fullMoney; //满
    private float addMoney; //送
    private String createTime;
    private int status=1; //1：生效，-1 不生效
    private int validType=1; //1:一直有效，2：时间段有效
    private String startDate; //开始时间
    private String endDate; //结束时间
    private String cardType; //字符串，逗号分隔
    @Transient
    private String card_type_name;
}