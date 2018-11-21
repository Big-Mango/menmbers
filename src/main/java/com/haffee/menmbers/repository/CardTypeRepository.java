package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * create by jacktong
 * date 2018/11/21 下午6:52
 **/

public interface CardTypeRepository extends JpaRepository<CardType,Integer> {

    @Query(value = "select * from card_type where shop_id = ?1 order by id desc",nativeQuery = true)
    List<CardType> findAllByShop_id(int shop_id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update card_type set card_name = ?2 where id = ?1",nativeQuery = true)
    void rename(int id,String name);
}
