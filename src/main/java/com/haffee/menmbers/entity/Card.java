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
 * date 2018/7/16 下午7:15
 * 会员卡信息
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private String cardNo;
    private int shopId;//商户id
    private String cardCreateTime; //发卡时间
    private int cardStatus=1; //卡状态 1：正常，0 冻结，-1：挂失，-2 损坏
    private int cardType=1;//卡状态 1:储值卡 2:折扣卡
    private String remark;
    private float balance = 0; //余额
    private int jifen = 0;//积分
    private int userId; //user标识
}
