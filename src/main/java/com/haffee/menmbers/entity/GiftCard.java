package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * create by liujia
 * date 2018/9/13 上午8:15
 * 礼品卡信息
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class GiftCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private String cardNo;//卡号
    private int shopId;//商户id
    private int status=0; //卡状态 0：未使用， 1：已使用
    private int type=0;//卡类型 0:礼品卡
    private String createTime; //发卡时间
    private String remark;//描述
    @Transient
    private Shop shop;
}
