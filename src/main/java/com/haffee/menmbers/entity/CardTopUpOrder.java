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
 * date 2018/7/16 下午8:17
 * 会员卡充值订单
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class CardTopUpOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private int user_id;
    private int card_id;
    private int shop_id;
    private float fee;
    private Date create_time;
    private Date payment_time;
    private int payment_status; //支付状态 1：支付成功 0：待支付，-1：支付失败
    private String order_desc;
    private int discount_id;//折扣方案 关联config
    private float discount_fee; //折扣金额
    private String discount_desc; //折扣描述


}
