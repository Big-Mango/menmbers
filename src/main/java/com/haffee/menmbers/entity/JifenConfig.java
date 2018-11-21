package com.haffee.menmbers.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * create by jacktong
 * date 2018/10/22 下午7:07
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class JifenConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int shopId;
    private int jifen; //1元等于多少积分
    private int type = 0;//0:长期有效，1：限时有效
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private int status = 1;//状态：0：未生效，1：生效
    private Date createTime;
    private String cardType; //字符串，逗号分隔 适用卡类型
    @Transient
    private String card_type_name;
}
