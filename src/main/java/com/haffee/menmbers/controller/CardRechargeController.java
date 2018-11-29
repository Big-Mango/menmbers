package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.CardRecharge;
import com.haffee.menmbers.service.CardRechargeService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
* @Description:    充值卡管理
* @Author:         liujia
* @CreateDate:     2018/7/29 10:25
* @Version:        1.0
*/
@RestController
@RequestMapping("/recharge")
public class CardRechargeController {
    @Resource
    private CardRechargeService cardRechargeService;
    /**
    * 方法实现说明 按照创建时间倒序排列查询所有记录
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/7/29 10:47
    */
    @PostMapping("/findAll")
    public ResponseMessage findAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "create_time") String sort,@RequestParam(defaultValue = "0") int shopId){
        try {
            if(page>0){
                page = page -1;
            }
            if(size==0){
                size = 10;
            }
            Page<CardRecharge> pageRecharge= cardRechargeService.findAllByShopId(PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sort)), shopId);
            return ResponseMessage.getResponseMessage(pageRecharge);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
    
    /**
    * 方法实现说明 充值卡新增
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/7/29 11:00
    */
    @PostMapping("/add")
    public ResponseMessage add(CardRecharge cardRecharge){
        try {
            CardRecharge responseCardRecharge = cardRechargeService.add(cardRecharge);
            return ResponseMessage.getResponseMessage(responseCardRecharge);
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
    @PostMapping("/findByCardNo")
    public ResponseMessage findByCardNo(@RequestParam String cardNo,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "createTime") String sort){
        try {
            Page<CardRecharge> cardRecharge = cardRechargeService.findByCardNo(cardNo,PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sort)));
            return ResponseMessage.getResponseMessage(cardRecharge);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 方法实现说明  根据手机号查询一条记录
     * @author      liujia
     * @return  ResponseMessage
     * @exception
     * @date        2018/7/29 10:54
     */
    @PostMapping("/findByUserPhone")
    public ResponseMessage findByUserPhone(@RequestParam String userPhone,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "createTime") String sort){
        try {
            if(page>0){
                page = page -1;
            }
            if(size==0){
                size = 10;
            }
            Page<CardRecharge> cardRecharge = cardRechargeService.findByUserPhone(userPhone,PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sort)));
            return ResponseMessage.getResponseMessage(cardRecharge);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
    /**
     * 方法实现说明  根据id查询一条记录
     * @author      liujia
     * @return  ResponseMessage
     * @exception
     * @date        2018/7/29 10:54
     */
    @PostMapping("/findById")
    public ResponseMessage findById(@RequestParam int id){
        try {
            Optional<CardRecharge> cardRecharge = cardRechargeService.findById(id);
            if(cardRecharge.isPresent()){
                return ResponseMessage.getResponseMessage(cardRecharge.get());
            }else{
                return ResponseMessage.error();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
}
