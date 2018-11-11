package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.CardConsume;
import com.haffee.menmbers.entity.PaymentOrder;
import com.haffee.menmbers.service.PaymentOrderService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by jacktong
 * date 2018/11/6 下午6:48
 **/

@RestController
@RequestMapping("/order")
public class PaymentOrderController {

    @Autowired
    private PaymentOrderService paymentOrderService;

    /**
     * 查询订单
     * @param order_id
     * @return
     */
    @GetMapping("/findOne")
    public ResponseMessage findOne(String order_id){
        try {
            PaymentOrder order = paymentOrderService.findOne(order_id);
            return ResponseMessage.success(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 支付
     * @param cardConsume
     * @param yh_id
     * @return
     */
    @PostMapping("/doPayment")
    public ResponseMessage doPayment(CardConsume cardConsume, String yh_id){
        try {
            boolean isSuccess = paymentOrderService.doPayment(cardConsume,yh_id);
            if(isSuccess){
                return ResponseMessage.success();
            }else{
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 加载用户订单
     * @param user_id
     * @return
     */
    @GetMapping("/findByUser")
    public ResponseMessage findByUser(String user_id){
        try {
            List<PaymentOrder> list = paymentOrderService.findByUserId(user_id);
            return ResponseMessage.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

}
