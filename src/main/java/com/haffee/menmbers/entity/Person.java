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
 * date 2018/7/16 下午7:13
 * 会员本人信息
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private String realName;
    private int sex;
    private String phoneNo;
    private String address;
    private int label; //待定
    private int job; //待定

}
