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
    @GeneratedValue
    private int id; //主键唯一标识
    private String card_no;
    private Date card_create_time; //发卡时间
    private int card_status; //卡状态 1：正常，0：挂失，-1 删除，-2 损坏
    private String remark;

}