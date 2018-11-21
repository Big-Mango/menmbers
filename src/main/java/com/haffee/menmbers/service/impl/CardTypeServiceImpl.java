package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.CardType;
import com.haffee.menmbers.repository.CardTypeRepository;
import com.haffee.menmbers.service.CardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * create by jacktong
 * date 2018/11/21 下午6:54
 **/

@Service
@Transactional
public class CardTypeServiceImpl implements CardTypeService {

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @Override
    public List<CardType> findAll(int shop_id) {
        return cardTypeRepository.findAllByShop_id(shop_id);
    }

    @Override
    public CardType save(CardType cardType) {
        return cardTypeRepository.save(cardType);
    }

    @Override
    public boolean delete(CardType cardType) {
        cardTypeRepository.delete(cardType);
        return true;
    }

    @Override
    public boolean rename(int id, String name) {
        cardTypeRepository.rename(id,name);
        return true;
    }
}
