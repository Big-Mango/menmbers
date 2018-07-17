package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by jacktong
 * date 2018/7/17 下午7:40
 **/

public interface CardRepoitory extends JpaRepository<Card,Long> {
}
