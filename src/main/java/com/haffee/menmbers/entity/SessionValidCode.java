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
 * date 2018/9/26 下午8:40
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class SessionValidCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sessionId;
    private String validCode;
}
