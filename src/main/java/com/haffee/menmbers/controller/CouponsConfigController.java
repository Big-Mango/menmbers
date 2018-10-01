package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.CouponsConfig;
import com.haffee.menmbers.service.CouponsConfigService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * create by jacktong
 * date 2018/9/28 下午8:15
 **/

@RestController
@RequestMapping("/coupons/config")
public class CouponsConfigController {

    @Autowired
    private CouponsConfigService service;

    /**
     * 新增优惠券配置方案
     * @param config
     * @return
     */
    @PostMapping("/add")
    public @ResponseBody Object addCouponsConfig(CouponsConfig config){
        try {
            service.add_config(config);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 删除方案
     * @param config
     * @return
     */
    @PostMapping("/delete")
    public @ResponseBody Object deleteConfig(CouponsConfig config){
        try {
            service.delete_config(config);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 修改状态
     * @param config_id
     * @param status
     * @return
     */
    @PostMapping("/changeStatus")
    public @ResponseBody Object changeStatus(int config_id,int status){
        try {
            service.changeStatus(config_id,status);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 查询全部
     * @param shop_id
     * @return
     */
    @GetMapping("/findAll")
    public @ResponseBody Object findAllByShop(int shop_id){
        try {
            List<CouponsConfig> list = service.findAll(shop_id);
            return ResponseMessage.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }




}
