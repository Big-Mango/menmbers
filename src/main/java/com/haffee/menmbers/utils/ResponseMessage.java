package com.haffee.menmbers.utils;

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
    private String code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    public static ResponseMessage success() {
        return new ResponseMessage(Constant.SUCCESS_CODE, Constant.SUCCESS_MESSAGE, null);
    }

    public static ResponseMessage success(Object data) {
        return new ResponseMessage(Constant.SUCCESS_CODE, Constant.SUCCESS_MESSAGE, data);
    }

    public static ResponseMessage error() {
        return new ResponseMessage(Constant.ERROR_CODE, Constant.ERROR_MESSAGE, null);
    }

    public static ResponseMessage errorWithMsg(String msg) {
        return new ResponseMessage(Constant.ERROR_CODE, msg, null);
    }

    public static ResponseMessage getResponseMessage(Object data){
        if (data!=null) {
            return success(data);
        }else{
            return error();
        }
    }
}
