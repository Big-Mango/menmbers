package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.Coupons;
import com.haffee.menmbers.service.CouponsService;
import com.haffee.menmbers.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by jacktong
 * date 2018/9/28 下午9:13
 **/

@RestController
@RequestMapping("/coupons/use")
public class CouponsController {

    @Autowired
    private CouponsService service;

    /**
     * 加载优惠券
     * @param user_id
     * @param shop_id
     * @param order_fee
     * @return
     */
   @GetMapping("/findEnableByUserAndShop")
       public @ResponseBody Object findAllByShop(int user_id,int shop_id,float order_fee){
           try {
               List<Coupons> list = service.findEnableCouponsByUserAndShop(shop_id,user_id,order_fee);
               return ResponseMessage.success(list);
           } catch (Exception e) {
               e.printStackTrace();
               return ResponseMessage.error();
           }
       }

    /**
     * 查询个人所有优惠券
      * @param user_id
     * @return
     */
   @GetMapping("/findAllByUser")
       public @ResponseBody Object findAllByShop(int user_id){
           try {
               List<Coupons> list = service.findAllCouponsByUser(user_id);
               return ResponseMessage.success(list);
           } catch (Exception e) {
               e.printStackTrace();
               return ResponseMessage.error();
           }
       }
}
