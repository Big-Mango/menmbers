package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * create by jacktong
 * date 2018/7/16 下午7:17
 * 会员卡充值消费记录
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class CardRecord {

    @Id
    @GeneratedValue
    private int id; //主键唯一标识
    private int type; //1:消费，8：充值
    private int user_id;//账户ID
    private String user_phone;
    private int card_id; //充值卡ID
    private String card_no; //卡号
    private float pay_fee; //实际支付金额
    private float balance; //操作之后账户余额
    private int if_discount; //是否有折扣 1：有，0 无
    private int discount_id;//折扣方案 关联config
    private float discount_fee; //折扣金额
    private String discount_desc; //折扣描述
    private Date create_time;
    private int payment_way; //默认0，如果充值，10：现金，20：微信静态码，21：微信收款，30：支付宝静态码，31：支付宝收款，90：其他
    private int shop_id;//消费店铺
    private String order_id;//消费订单id
    private String top_up_order_id; //充值订单ID


}
