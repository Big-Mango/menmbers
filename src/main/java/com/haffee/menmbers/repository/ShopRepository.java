package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by jacktong
 * date 2018/7/17 下午7:45
 **/

public interface ShopRepository extends JpaRepository<Shop,Integer> {

    @Query(value = "select * from shop where shop_name = ?1 ",nativeQuery = true)
    List<Shop> findByName(String shop_name);

}
