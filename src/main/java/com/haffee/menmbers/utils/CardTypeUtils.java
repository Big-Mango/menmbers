package com.haffee.menmbers.utils;

import org.apache.commons.lang.StringUtils;

/**
 * create by jacktong
 * date 2018/11/21 下午9:16
 **/

public class CardTypeUtils {

    public static boolean if_card_type_contain(String card_type,String card_type_str){
        boolean flag = false;
        if(StringUtils.isNotEmpty(card_type_str)){
            String [] array = card_type_str.split(",");
            for (String s:array ) {
                if(s.equals(card_type)){
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

}
