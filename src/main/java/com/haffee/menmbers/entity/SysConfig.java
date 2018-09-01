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
 * date 2018/9/1 下午12:43
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class SysConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String paramName;
    private String paramValue;
    private String comments;
    private int status = 1;

}
