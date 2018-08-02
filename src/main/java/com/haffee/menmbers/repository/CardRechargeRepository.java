package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.CardRecharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * create by jacktong
 * date 2018/7/17 下午7:42
 **/

public interface CardRechargeRepository extends JpaRepository<CardRecharge,Long> {
    @Query("select e from CardRecharge e,Card f where e.cardId = f.id and f.cardNo = ?1 ")
    CardRecharge findByCardNo(String cardNo);
}