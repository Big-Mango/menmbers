package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by jacktong
 * date 2018/10/3 上午11:31
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class NoticeItem {

    private String value;
    private String color;

}
