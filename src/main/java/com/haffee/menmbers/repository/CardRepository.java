package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
* @Description:    java类作用描述
* @Author:         liujia
* @CreateDate:     2018/7/29 10:02
* @Version:        1.0
*/
public interface CardRepository extends JpaRepository<Card,Integer> {
    Card findByCardNo(String cardNo);
}
