package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * create by jacktong
 * date 2018/11/1 下午7:39
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String user_id;
    private String shop_id;
    private String card_no;
    private String order_no;
    private String order_content;//订单内容
    private int status = 0; //0:待支付，1：已支付，-1：取消
    private float payment;//订单金额
    private float real_payment = 0; //实付金额
    private String diancai_order_id; //点菜系统ID
    private String youhui_content;
    private Date payment_time;
    private Date create_time;
    @Transient
    Shop shop;


}
