package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.ManjianConfig;
import com.haffee.menmbers.service.ManjianConfigService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

/**
 * create by jacktong
 * date 2018/10/9 下午7:55
 **/

@RestController
@RequestMapping("/manjian")
public class ManjianConfigController {

    @Autowired
    private ManjianConfigService manjianConfigService;

    /**
     * 查询全部 根据店铺
     * @param shop_id
     * @return
     */
    @GetMapping("/findallByShop")
    public ResponseMessage findAllByShop(String shop_id){
        try {
            List<ManjianConfig> list = manjianConfigService.findAllByShop(shop_id);
            return ResponseMessage.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 查询单个
     * @param shop_id
     * @return
     */
    @GetMapping("/findEnable")
    public ResponseMessage findOEnableByShop(String shop_id){
        try {
            ManjianConfig config = manjianConfigService.findOneEnable(shop_id);
            return ResponseMessage.success(config);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 删除
     * @param config_id
     * @return
     */
    @PostMapping("/delete")
    public ResponseMessage delete(int config_id){
        try {
            ManjianConfig config = new ManjianConfig();
            config.setId(config_id);
            manjianConfigService.deleteConfig(config);
            return ResponseMessage.success();
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
    public ResponseMessage add(ManjianConfig config){
        try {
            manjianConfigService.addConfig(config);
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
    public ResponseMessage changeStatus(int config_id,int status){
        try {
            manjianConfigService.changeStatus(config_id,status);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }



}
