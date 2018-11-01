package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by jacktong
 * date 2018/11/1 下午8:02
 **/

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Integer> {
}
