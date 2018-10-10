package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.RealDiscountConfig;
import com.haffee.menmbers.service.RealDiscountService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by jacktong
 * date 2018/10/9 下午7:56
 **/

@RestController
@RequestMapping("/realdiscount")
public class RealDiscountConfigController {

    @Autowired
    private RealDiscountService realDiscountService;

    /**
     * 查询全部 根据店铺
     * @param shop_id
     * @return
     */
    @GetMapping("/findAllByShop")
    public ResponseMessage findAll(String shop_id){
        try {
            List<RealDiscountConfig> list = realDiscountService.findAllByShop(shop_id);
            return ResponseMessage.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 查询可用折扣配置
     * @param shop_id
     * @return
     */
    @GetMapping("/findOneEnable")
    public ResponseMessage findOneEnable(String shop_id){
        try {
            RealDiscountConfig config = realDiscountService.findOneEnable(shop_id);
            return ResponseMessage.success(config);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 新增
     * @param config
     * @return
     */
    @PostMapping("/add")
    public ResponseMessage add(RealDiscountConfig config){
        try {
            realDiscountService.addConfig(config);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 删除
     * @param config
     * @return
     */
    @PostMapping("/delete")
    public ResponseMessage delete(RealDiscountConfig config){
        try {
            realDiscountService.deleteConfig(config);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 更新状态
     * @param config_id
     * @param status
     * @return
     */
    @PostMapping("/changeStatus")
    public ResponseMessage changeStatus(int config_id,int status){
        try {
            realDiscountService.changeStatus(config_id,status);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

}
