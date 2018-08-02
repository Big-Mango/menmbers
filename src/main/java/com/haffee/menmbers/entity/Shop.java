package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * create by jacktong
 * date 2018/7/16 下午7:34
 * 开通店铺
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private String shopName;
    private String shopAddr;
    private int area;//营业面积
    private String contanct; //联系电话
    private String ownerName;
    private String ownerPhone;
    private String openTime; //营业时间
    private String closeTime; //关店时间
    private int tableCount; //卓台数



}
