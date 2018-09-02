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
 * date 2018/9/2 下午1:02
 * 短信模板 参数用 & 符号代替
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class SmsTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String template_code;
    private String template_content;
    private String remark;
    private int status = 1; //1:正常使用，其他：不使用

}
