package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.CouponsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * create by jacktong
 * date 2018/9/28 下午8:11
 **/

public interface CouponsConfigRepository extends JpaRepository<CouponsConfig,Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="update coupons_config set status = ?2 where id=?1",nativeQuery = true)
    void changeStatus(int config_id,int status);

    @Query(value="select * from coupons_config where shop_id=?1 order by create_time desc",nativeQuery = true)
    List<CouponsConfig> findAllByShopId(int shop_id);

    @Query(value="select * from coupons_config where shop_id=?1 and first_user_give=1",nativeQuery = true)
    List<CouponsConfig> findByShopAndFirstSent(int shop_id);
}
