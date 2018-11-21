package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.CardType;

import java.util.List;

/**
 * create by jacktong
 * date 2018/11/21 下午6:53
 **/

public interface CardTypeService {

    List<CardType> findAll(int shop_id);

    CardType save(CardType cardType);

    boolean delete(CardType cardType);

    boolean rename(int id,String name);
}
