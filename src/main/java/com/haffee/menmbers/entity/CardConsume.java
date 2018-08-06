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
 * date 2018/7/16 下午7:17
 * 会员卡充值消费记录
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class CardConsume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private int type; //1:消费，8：充值
    private int userId;//账户ID
    private String userPhone;
    private int cardId; //充值卡ID
    private String cardNo; //卡号
    private float payFee; //实际支付金额
    private float balance; //操作之后账户余额
    private int ifDiscount; //是否有折扣 1：有，0 无
    private int discountId;//折扣方案 关联config
    private float discountFee; //折扣金额
    private String discountDesc; //折扣描述
    private Date createTime;
    private int paymentWay=0; //默认0，如果充值，10：现金，20：微信静态码，21：微信收款，30：支付宝静态码，31：支付宝收款，90：其他
    private int shopId;//消费店铺
    private String orderId;//消费订单id
    private int rechargeId; //充值订单ID


}
