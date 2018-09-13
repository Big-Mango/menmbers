package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.repository.*;
import com.haffee.menmbers.service.CardService;
import com.haffee.menmbers.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description:    礼品卡管理
 * @Author:         liujia
 * @CreateDate:     2018/9/13 8:15
 * @Version:        1.0
 */
@Service
@Transactional
public class GiftCardServiceImpl implements GiftCardService {

    @Resource
    private GiftCardRepository giftCardRepository;

    @Autowired
    private ShopRepository shopRepository;

    /**
     * 查询分页
     *
     * @param pageable
     * @return
     * @throws Exception
     */

    public Page<GiftCard> findAll(Pageable pageable,int shopId,int status) {
        Page<GiftCard> page = giftCardRepository.findAllByShopId(pageable,shopId,status);
        for(GiftCard card : page.getContent()){
            Optional<Shop> shop = shopRepository.findById(card.getShopId());
            if(shop.isPresent()){
                card.setShop(shop.get());
            }
        }
        return page;
    }

    public GiftCard findByCardNo(String cardNo) {
        GiftCard card = giftCardRepository.findByCardNo(cardNo);
        if(card!=null){
            Optional<Shop> shop = shopRepository.findById(card.getShopId());
            if(shop.isPresent()){
                card.setShop(shop.get());
            }
        }
        return card;
    }

    public int add(GiftCard card) {
        GiftCard giftCard = giftCardRepository.findByCardNo(card.getCardNo());
        if (giftCard!=null) {
            return 0;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(new Date());
            card.setCreateTime(createTime);
            giftCardRepository.save(card);
            return 1;
        }
    }

    public GiftCard update(String cardNo,int status) {
        GiftCard card = giftCardRepository.findByCardNo(cardNo);
        card.setStatus(status);
        return giftCardRepository.save(card);
    }

    public void delete(GiftCard card) {
        giftCardRepository.delete(card);
    }
}
