package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
* @Description:    java类作用描述
* @Author:         liujia
* @CreateDate:     2018/7/29 9:55
* @Version:        1.0
*/
public interface CardService {
    Page<Card> findAll(Pageable pageable);
    Card findByCardNo(String cardNo);
    Optional<Card> findById(int id);
    Card add(Card card);
    Card update(Card card);
    void delete(Card card);
    Card changeCardStatus(String cardNo,int cardStatus);
    void replace(String oldCardNo,String newCardNo);
}
