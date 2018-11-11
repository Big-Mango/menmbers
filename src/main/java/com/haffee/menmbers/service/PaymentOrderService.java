package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.CardConsume;
import com.haffee.menmbers.entity.PaymentOrder;

import java.util.List;

/**
 * create by jacktong
 * date 2018/11/6 下午6:50
 **/

public interface PaymentOrderService {

    PaymentOrder findOne(String order_id);

    boolean doPayment(CardConsume cardConsume, String yh_id);

    List<PaymentOrder> findByUserId(String user_id);

}
