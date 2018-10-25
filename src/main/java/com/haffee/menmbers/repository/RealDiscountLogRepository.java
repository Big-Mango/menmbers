package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.RealDiscountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * create by jacktong
 * date 2018/10/23 下午7:57
 **/

public interface RealDiscountLogRepository extends JpaRepository<RealDiscountLog,Integer> {

    @Query(value = "select count(1) from real_discount_log where user_id=?1 and use_time like CONCAT('%',?2,'%')",nativeQuery = true)
    int selectDiscountUseTimesMonth(String user_id,String month);
}
