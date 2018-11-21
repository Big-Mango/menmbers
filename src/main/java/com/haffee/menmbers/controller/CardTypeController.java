package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.CardType;
import com.haffee.menmbers.service.CardTypeService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by jacktong
 * date 2018/11/21 下午6:59
 **/

@RestController
@RequestMapping("/cardType")
public class CardTypeController {

    @Autowired
    private CardTypeService cardTypeService;

    /**
     * 查询店铺全部卡类型
     * @param shop_id
     * @return
     */
    @GetMapping("/findAll")
    public ResponseMessage findAll(int shop_id){
        try {
            List<CardType> list = cardTypeService.findAll(shop_id);
            return ResponseMessage.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 新增
     * @param cardType
     * @return
     */
    @PostMapping("/save")
    public ResponseMessage save(CardType cardType){
        try {
            cardTypeService.save(cardType);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 删除
     * @param cardType
     * @return
     */
    @PostMapping("/delete")
    public ResponseMessage delete(CardType cardType){
        try {
            cardTypeService.delete(cardType);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 改名
     * @param id
     * @param name
     * @return
     */
    @PostMapping("/rename")
    public ResponseMessage rename(int id,String name){
        try {
            cardTypeService.rename(id,name);
            return ResponseMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

}
