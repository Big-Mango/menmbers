package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.User;

/**
 * create by jacktong
 * date 2018/9/1 下午1:42
 **/

public interface CustomerService {

    User checkUserPhone(String phone_no,String openid,String access_token,String refesh_token,String acc_code);

}
