package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.CardConsume;
import com.haffee.menmbers.entity.CardRecharge;
import com.haffee.menmbers.service.CardConsumeService;
import com.haffee.menmbers.service.CardRechargeService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
* @Description:    卡消费管理
* @Author:         liujia
* @CreateDate:     2018/8/6 10:25
* @Version:        1.0
*/
@RestController
@RequestMapping("/consume")
public class CardConsumeController {
    @Resource
    private CardConsumeService cardConsumeService;
    /**
    * 方法实现说明 按照创建时间倒序排列查询所有记录
    * @author      liujia
    * @return  ResponseMessage
    * @exception   
    * @date        2018/8/6 10:47
    */
    @PostMapping("/findAll")
    public ResponseMessage findAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "createTime") String sort,int shopId){
        try {
            if(page>0){
                page = page -1;
            }
            if(size==0){
                size = 10;
            }
            Page<CardConsume> pageConsume= cardConsumeService.findAllByShopId(PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sort)),shopId);
            return ResponseMessage.getResponseMessage(pageConsume);
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
    * @date        2018/8/6 11:00
    */
    @PostMapping("/add")
    public ResponseMessage add(CardConsume cardConsume,String yh_id){
        try {
            CardConsume responseCardConsume = cardConsumeService.add(cardConsume,yh_id);
            if(null==responseCardConsume){
                return ResponseMessage.errorWithMsg("此账户信息消费异常，请检查账户状态及余额");
            }else{
                return ResponseMessage.getResponseMessage(responseCardConsume);
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
     * @date        2018/8/6 10:54
     */
    @PostMapping("/findByCardNo")
    public ResponseMessage findByCardNo(@RequestParam String cardNo,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "createTime") String sort){
        try {
            Page<CardConsume> cardRConsume = cardConsumeService.findByCardNo(cardNo,PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sort)));
            return ResponseMessage.getResponseMessage(cardRConsume);
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
     * @date        2018/8/6 10:54
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
            Page<CardConsume> cardConsume = cardConsumeService.findByUserPhone(userPhone,PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sort)));
            return ResponseMessage.getResponseMessage(cardConsume);
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
     * @date        2018/8/6 10:54
     */
    @PostMapping("/findById")
    public ResponseMessage findById(@RequestParam int id){
        try {
            Optional<CardConsume> cardConsume = cardConsumeService.findById(id);
            if(cardConsume.isPresent()){
                return ResponseMessage.getResponseMessage(cardConsume.get());
            }else{
                return ResponseMessage.error();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
}
