package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * create by jacktong
 * date 2018/10/3 上午11:15
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class WechatNoticeForm {
    private String touser ;
    private String template_id;
    private String url;
    private Map<String,NoticeItem> data;
}
