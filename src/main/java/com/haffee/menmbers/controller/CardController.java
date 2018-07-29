package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.service.CardService;
import com.haffee.menmbers.utils.ResponseMessage;
import com.haffee.menmbers.utils.UuidUtils;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
* @Description:    会员卡管理
* @Author:         liujia
* @CreateDate:     2018/7/29 10:25
* @Version:        1.0
*/
@RestController
@RequestMapping("/card")
public class CardController {
    @Resource
    private CardService cardService;
    /**
    * 方法实现说明 按照开卡时间倒序排列查询所有记录
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/7/29 10:47
    */
    @GetMapping("/findAll")
    public ResponseMessage findAll(){
        try {
            List<Card> list = cardService.findAll(Sort.by(Sort.Direction.DESC,"cardCreateTime"));
            return ResponseMessage.getResponseMessage(list);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
    
    /**
    * 方法实现说明 根据id查询一条记录，多用于详细信息展示
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/7/29 10:49
    */
    @GetMapping("/findById")
    public ResponseMessage findById(long id){
        try {
            Optional<Card> card = cardService.findById(id);
            if(card.isPresent()){
                return ResponseMessage.getResponseMessage(card.get());
            }else{
                return ResponseMessage.error();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
    
    /**
    * 方法实现说明  根据卡号查询一条记录
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/7/29 10:54
    */
    @GetMapping("/findByCardNo")
    public ResponseMessage findByCardNo(String cardNo){
        try {
            Card card = cardService.findByCardNo(cardNo);
            return ResponseMessage.getResponseMessage(card);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**  会员卡信息新增
    * 方法实现说明
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/7/29 11:00
    */
    @PostMapping("/add")
    public ResponseMessage add(@RequestBody Card card){
        try {
            String id = UuidUtils.getUUID32();
            Card responseCard = cardService.add(card);
            return ResponseMessage.getResponseMessage(responseCard);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**  会员卡信息修改
     * 方法实现说明
     * @author      liujia
     * @return  ResponseMessage
     * @exception
     * @date        2018/7/29 11:00
     */
    @PostMapping("/update")
    public ResponseMessage update(@RequestBody Card card){
        try {
            Card responseCard = cardService.update(card);
            return ResponseMessage.getResponseMessage(responseCard);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
    * 方法实现说明 根据id删除会员卡信息
    * @author      liujia
    * @return ResponseMessage
    * @exception
    * @date        2018/7/29 11:09
    */
    @GetMapping("/delete")
    public ResponseMessage delete(long id){
        try {
            cardService.delete(id);
            return ResponseMessage.success();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
}
