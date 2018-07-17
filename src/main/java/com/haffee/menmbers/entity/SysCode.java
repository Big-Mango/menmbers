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
 * 系统二级代码
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class SysCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //主键唯一标识
    private String code;  //代码 如：SEX
    private String code_value; //值：1
    private String code_str; //展示：男
    private String content; // 如：性别
    private int order_index; //排序字段

}
