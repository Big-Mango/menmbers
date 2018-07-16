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
 * date 2018/7/16 下午7:19
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class SmsRecord {

    @Id
    @GeneratedValue
    private int id; //主键唯一标识
    private String send_uuid;//发送唯一标识
    private String valid_code; //验证码
    private String phone;
    private String content;
    private int status; //0:发送中，1：成功
    private Date create_time;
    private Date success_time;


}
