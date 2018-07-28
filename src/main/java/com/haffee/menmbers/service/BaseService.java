package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.SysCode;

import java.util.List;

/**
 * create by jacktong
 * date 2018/7/26 下午6:29
 * 通用接口--基础功能
 **/

public interface BaseService {

    List<SysCode> selectByCode(String code) throws Exception;
}
