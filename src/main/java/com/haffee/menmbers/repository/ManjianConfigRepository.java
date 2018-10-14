package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.ManjianConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by jacktong
 * date 2018/10/9 下午7:36
 **/

public interface ManjianConfigRepository extends JpaRepository<ManjianConfig,Integer> {

    @Query(value = "select * from manjian_config where shop_id = ?1 order by create_time desc",nativeQuery = true)
    List<ManjianConfig> findAllByShopId(String shop_id);

    @Query(value="select * from manjian_config where shop_id = ?1 and status = 1 and (type=0 or (now() >= start_date and now() <= end_date)) order by create_time desc limit 1",nativeQuery = true)
    ManjianConfig findOneEnableByShopId(String shop_id);
}
