package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.DiscountConfig;
import com.haffee.menmbers.service.CardService;
import com.haffee.menmbers.service.DiscountConfigService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
* @Description:    优惠管理
* @Author:         liujia
* @CreateDate:     2018/7/29 10:25
* @Version:        1.0
*/
@RestController
@RequestMapping("/discount")
public class DiscountConfigController {
    @Resource
    private DiscountConfigService discountConfigService;
    /**
    * 方法实现说明 按照创建时间倒序排列查询所有记录
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/7/29 10:47
    */
    @PostMapping("/findAll")
    public ResponseMessage findAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "createTime") String sort){
        try {
            Page<DiscountConfig> pageCard = discountConfigService.findAll(PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sort)));
            return ResponseMessage.getResponseMessage(pageCard);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
    
    /**
    * 方法实现说明 优惠配置新增
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/7/29 11:00
    */
    @PostMapping("/add")
    public ResponseMessage add(@RequestBody DiscountConfig discountConfig){
        try {
            DiscountConfig responseDiscountConfig = discountConfigService.add(discountConfig);
            return ResponseMessage.getResponseMessage(responseDiscountConfig);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 方法实现说明 优惠配置修改
     * @author      liujia
     * @return  ResponseMessage
     * @exception
     * @date        2018/7/29 11:00
     */
    @PostMapping("/update")
    public ResponseMessage update(@RequestBody DiscountConfig discountConfig){
        try {
            DiscountConfig responseDiscountConfig = discountConfigService.update(discountConfig);
            return ResponseMessage.getResponseMessage(responseDiscountConfig);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
    * 方法实现说明 优惠配置删除,直接用jpa的delete方法，如果调用deleteById方法，输入的id不在数据库范围内，会报不存在该id的异常，
     * 而用delete方法，则没有该问题
    * @author      liujia
    * @return ResponseMessage
    * @exception
    * @date        2018/7/29 11:09
    */
    @PostMapping("/delete")
    public ResponseMessage delete(@RequestBody DiscountConfig discountConfig){
        try {
            discountConfigService.delete(discountConfig);
            return ResponseMessage.success();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
}
