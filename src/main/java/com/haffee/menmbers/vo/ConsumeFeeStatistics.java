package com.haffee.menmbers.vo;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Serialization
public class ConsumeFeeStatistics {
    private String realName;
    private String userPhone;
    private Double payFee;
}
