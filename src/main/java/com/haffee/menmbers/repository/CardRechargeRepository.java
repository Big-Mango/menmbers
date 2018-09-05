package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.CardRecharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * create by jacktong
 * date 2018/7/17 下午7:42
 **/

public interface CardRechargeRepository extends JpaRepository<CardRecharge,Integer> {
    Page<CardRecharge> findByCardNo(String cardNo, Pageable pageable);
    Page<CardRecharge> findByUserPhone(String userPhone, Pageable pageable);
    Page<CardRecharge> findByShopId(int shopId,Pageable pageable);
}