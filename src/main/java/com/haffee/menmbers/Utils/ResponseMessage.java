package com.haffee.menmbers.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by jacktong
 * date 2018/7/16 下午8:50
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    /**
     * 返回状态
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;

    public static ResponseMessage success() {
        return new ResponseMessage(1, "success", null);
    }

    public static ResponseMessage success(Object data) {
        return new ResponseMessage(0, "success", data);
    }

    public static ResponseMessage error() {
        return new ResponseMessage(-1, "error", null);
    }




}
