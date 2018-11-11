package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by jacktong
 * date 2018/11/1 下午8:02
 **/

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Integer> {

    @Query(value = "select * from payment_order where user_id = ?1 order by create_time desc",nativeQuery = true)
    List<PaymentOrder> findByUser_id(String user_id);
}
