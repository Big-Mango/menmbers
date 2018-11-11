package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardConsume;
import com.haffee.menmbers.entity.PaymentOrder;
import com.haffee.menmbers.entity.Shop;
import com.haffee.menmbers.repository.PaymentOrderRepository;
import com.haffee.menmbers.repository.ShopRepository;
import com.haffee.menmbers.service.CardConsumeService;
import com.haffee.menmbers.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * create by jacktong
 * date 2018/11/6 下午6:50
 **/

@Service
@Transactional
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Autowired
    private PaymentOrderRepository orderRepository;

    @Autowired
    private CardConsumeService cardConsumeService;

    @Autowired
    private ShopRepository shopRepository;


    /**
     * 查询待支付订单
     * @param order_id
     * @return
     */
    @Override
    public PaymentOrder findOne(String order_id) {
        Optional<PaymentOrder> o = orderRepository.findById(Integer.valueOf(order_id));
        if(o.isPresent()){
            Optional<Shop> optional = shopRepository.findById(Integer.valueOf(o.get().getShop_id()));
            if(optional.isPresent()){
                o.get().setShop(optional.get());
            }
            return o.get();
        }else{
            return null;
        }

    }

    /**
     * 公众号支付
     * @param
     * @return
     */
    @Override
    public boolean doPayment(CardConsume cardConsume, String yh_id) {
        boolean isSuccess = false;
        Optional<PaymentOrder> o = orderRepository.findById(Integer.valueOf(cardConsume.getOrderId()));
        if(o.isPresent()){
            PaymentOrder order = o.get();
            //新增消费记录
            cardConsumeService.add(cardConsume,yh_id);
            //更新订单状态
            order.setStatus(1);
            order.setPayment_time(new Date());
            order.setReal_payment(cardConsume.getPayFee());
            orderRepository.save(order);
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * 查询订单
     * @param user_id
     * @return
     */
    @Override
    public List<PaymentOrder> findByUserId(String user_id) {
        List<PaymentOrder> list = orderRepository.findByUser_id(user_id);
        for (PaymentOrder o:list) {
            Optional<Shop> optional = shopRepository.findById(Integer.valueOf(o.getShop_id()));
            if(optional.isPresent()){
                o.setShop(optional.get());
            }
        }
        return list;
    }
}
