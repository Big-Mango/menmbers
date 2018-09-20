package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.CardConsume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by jacktong
 * date 2018/7/17 下午7:41
 **/

public interface CardConsumeRepository extends JpaRepository<CardConsume,Integer> {
    Page<CardConsume> findByCardNo(String cardNo, Pageable pageable);
    Page<CardConsume> findByUserPhone(String userPhone, Pageable pageable);
    Page<CardConsume> findByShopId(int shopId, Pageable pageable);
    @Query(value = "select sum(pay_fee)fee from card_consume where shop_id = ?1 and substr(create_time,1,10)=?2",nativeQuery = true)
    float getConsumeFeeToday(int shopId,String today);

    @Query(value = "select sum(c.pay_fee) pay_fee ,c.user_phone,p.real_name from card_consume c,person p,user u where c.shop_id = ?1 and c.user_id = u.id and u.person_id = p.id group by c.user_phone,p.real_name order by pay_fee desc ",nativeQuery = true)
    List<Object> getConsumeFeeList(int shopId, Pageable pageable);

    @Query(value = "select count(c.pay_fee) count ,c.user_phone,p.real_name from card_consume c,person p,user u where c.shop_id = ?1 and c.user_id = u.id and u.person_id = p.id group by c.user_phone,p.real_name order by count desc ",nativeQuery = true)
    List<Object> getConsumeCountList(int shopId, Pageable pageable);

    @Query(value = "select sum(c.fee) fee ,c.user_phone,p.real_name from card_recharge c,person p,user u where c.shop_id = ?1 and c.user_id = u.id and u.person_id = p.id group by c.user_phone,p.real_name order by fee desc ",nativeQuery = true)
    List<Object> getRechargeFeeList(int shopId, Pageable pageable);
}
