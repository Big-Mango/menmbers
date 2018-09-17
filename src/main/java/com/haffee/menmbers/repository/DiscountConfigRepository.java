package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.DiscountConfig;
import com.haffee.menmbers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by jacktong
 * date 2018/7/17 下午7:43
 **/

public interface DiscountConfigRepository extends JpaRepository<DiscountConfig,Integer> {

    /**
     * 获取小于等于fee的折扣方案
     * @param fee
     * @return
     */
    @Query(value="select d from DiscountConfig d where d.fullMoney<= ?1 and d.shopId = ?2")
    List<DiscountConfig> findByFullMoney(float fee,int shopId);

    @Query(value="select d from DiscountConfig d where d.shopId = ?1")
    Page<DiscountConfig> findAll(Pageable page,int shopId);

}
