package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.GiftCard;
import com.haffee.menmbers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @Description:    礼品卡管理
 * @Author:         liujia
 * @CreateDate:     2018/9/13 8:15
 * @Version:        1.0
 */
public interface GiftCardService {
    Page<GiftCard> findAll(Pageable pageable,int shopId,int status);
    GiftCard findByCardNo(String cardNo);
    GiftCard add(GiftCard card);
    GiftCard update(String cardNo,int status);
    void delete(GiftCard card);
}
