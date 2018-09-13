package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.GiftCard;
import com.haffee.menmbers.service.GiftCardService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* @Description:    礼品卡管理
* @Author:         liujia
* @CreateDate:     2018/9/13 8:15
* @Version:        1.0
*/
@RestController
@RequestMapping("/giftCard")
public class GiftCardController {
    @Resource
    private GiftCardService giftCardService;
    /**
    * 方法实现说明 按照开卡时间倒序排列查询所有记录
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/9/13 8:15
    */
    @PostMapping("/findAll")
    public ResponseMessage findAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String sort, int shopId,int status){
        try {
            if(page>0){
                page = page -1;
            }
            if(size==0){
                size = 10;
            }
            Page<GiftCard> pageCard = giftCardService.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)),shopId,status);
            return ResponseMessage.getResponseMessage(pageCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
    
    /**
    * 方法实现说明  根据卡号查询一条记录
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/9/13 8:15
    */
    @PostMapping("/findByCardNo")
    public ResponseMessage findByCardNo(String cardNo){
        try {
            GiftCard card = giftCardService.findByCardNo(cardNo);
            if(card!=null){
                return ResponseMessage.success(card);
            }else{
                return ResponseMessage.errorWithMsg("未查询到相关卡信息");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**  礼品卡信息新增
    * 方法实现说明
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/9/13 8:15
    */
    @PostMapping("/add")
    public ResponseMessage add(GiftCard card){
        try {
            int flag = giftCardService.add(card);
            if(flag>0){
                return ResponseMessage.success();
            }else{
                return ResponseMessage.errorWithMsg("已经存在此卡信息，不能重复添加");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**  礼品卡兑换，修改status字段为已使用
     *
     * @author      liujia
     * @return  ResponseMessage
     * @exception
     * @date        2018/9/13 8:15
     */
    @PostMapping("/update")
    public ResponseMessage update(String cardNo,int status){
        try {
            GiftCard responseCard = giftCardService.update(cardNo,status);
            return ResponseMessage.getResponseMessage(responseCard);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
    * 方法实现说明 删除会员卡信息 ，删除后id不会恢复，如此次删除的是5，那么下次add会从6开始
    * @author      liujia
    * @return ResponseMessage
    * @exception
    * @date        2018/9/13 8:15
    */
    @PostMapping("/delete")
    public ResponseMessage delete(GiftCard card){
        try {
            giftCardService.delete(card);
            return ResponseMessage.success();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
}
