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
    private int shop_id;
    private float full_money; //满
    private float add_money; //送
    private Date create_time;
    private int status; //1：生效，-1 不生效
    private int valid_type; //1:一直有效，2：时间段有效
    private Date start_date; //开始时间
    private Date end_date; //结束时间




}
