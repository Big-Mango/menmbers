package com.haffee.menmbers.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * create by jacktong
 * date 2018/9/13 下午7:14
 **/

public class OrderNumUtils {

    public static String genOrderNum() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return str + rannum;// 当前时间
    }
}
