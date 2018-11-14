package com.haffee.menmbers.utils;

import java.util.UUID;

/**
 * create by jacktong
 * date 2018/7/24 下午8:25
 **/

public class UuidUtils {

    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    public static void main(String []args){
        System.out.println(getUUID32());
    }
}
