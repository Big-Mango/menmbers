package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by jacktong
 * date 2018/9/28 下午8:10
 **/

public interface CouponsRepository extends JpaRepository<Coupons, Integer> {

    @Query(value = "select * from coupons where user_id=?1 and shop_id=?2 and " +
            "(type=0 or (now() >= begin_time and now() <= end_time)) and " +
            "(min_use_fee=0 or min_use_fee<=?3)", nativeQuery = true)
    List<Coupons> findEnableCouponsByUserAndShop(int user_id, int shop_id, float order_fee);

    @Query(value="select * from coupons where user_id = ?1 order by create_time desc",nativeQuery = true)
    List<Coupons> findAllCouponsByUser(int user_id);

    @Query(value = "select * from coupons where id=?1 and user_id=?2 and shop_id=?3 and " +
            "(type=0 or (now() >= begin_time and now() <= end_time)) and " +
            "(min_use_fee=0 or min_use_fee<=?4)", nativeQuery = true)
    Coupons findEnableCouponsByUserAndShopAndId(int coupons_id,int user_id, int shop_id, float order_fee);
}
