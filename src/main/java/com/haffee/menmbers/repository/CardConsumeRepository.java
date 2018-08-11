package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.CardConsume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by jacktong
 * date 2018/7/17 下午7:41
 **/

public interface CardConsumeRepository extends JpaRepository<CardConsume,Integer> {
    Page<CardConsume> findByCardNo(String cardNo, Pageable pageable);
    Page<CardConsume> findByUserPhone(String userPhone, Pageable pageable);
}
