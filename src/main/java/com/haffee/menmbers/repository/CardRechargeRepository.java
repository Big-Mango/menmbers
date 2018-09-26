package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.CardRecharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * create by jacktong
 * date 2018/7/17 下午7:42
 **/

public interface CardRechargeRepository extends JpaRepository<CardRecharge,Integer> {
    Page<CardRecharge> findByCardNo(String cardNo, Pageable pageable);
    Page<CardRecharge> findByUserPhone(String userPhone, Pageable pageable);
    Page<CardRecharge> findByShopId(int shopId,Pageable pageable);

    @Query(value = "select * from card_recharge where order_no = ?1 and payment_status=0",nativeQuery = true)
    CardRecharge findOneByOrderno(String order_no);

    @Query(value = "select COALESCE(sum(fee),0)fee from card_recharge where shop_id = ?1",nativeQuery = true)
    float getRechargeFeeTotal(int shopId);

    @Query(value = "select COALESCE(sum(c.fee),0) fee,c.user_phone,p.real_name from card_recharge c,person p,user u where c.shop_id = ?1 and c.user_id = u.id and u.person_id = p.id group by c.user_phone,p.real_name order by fee desc ",nativeQuery = true)
    List<Object> getRechargeFeeList(int shopId, Pageable pageable);
}