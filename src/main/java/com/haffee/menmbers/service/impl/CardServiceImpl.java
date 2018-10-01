package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.repository.*;
import com.haffee.menmbers.service.BaseService;
import com.haffee.menmbers.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description: java类作用描述
 * @Author: liujia
 * @CreateDate: 2018/7/29 10:25
 * @Version: 1.0
 */
@Service
@Transactional
public class CardServiceImpl implements CardService {

    @Resource
    private CardRepository cardRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CouponsConfigRepository couponsConfigRepository;

    @Autowired
    private CouponsRepository couponsRepository;

    public Page<Card> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    public Card findByCardNo(String cardNo) {
        return cardRepository.findByCardNo(cardNo);
    }

    public Optional<Card> findById(int id) {
        Optional<Card> o = cardRepository.findById(id); //modify by jacktong 2018-9-6 find card by id fill shop info
        if(o.isPresent()){
            Optional<Shop> o_s = shopRepository.findById(o.get().getShopId());
            if(o_s.isPresent()){
                o.get().setShop(o_s.get());
            }
        }
        return o;
    }

    public Card add(Card card) {
        if (cardRepository.exists(Example.of(card))) {
            return null;
        } else {
            //1.判断是否有新开卡送优惠券活动
            List<CouponsConfig> list = couponsConfigRepository.findByShopAndFirstSent(card.getShopId());
            //2.如果有，给客户账户新增优惠券
            if(list.size()>0){
                for (CouponsConfig config:list) {
                    Coupons coupons = new Coupons();
                    coupons.setUserId(card.getUserId());
                    coupons.setShopId(config.getShopId());
                    coupons.setBeginTime(config.getBeginTime());
                    coupons.setEndTime(config.getEndTime());
                    coupons.setCoupon_value(config.getCoupon_value());
                    coupons.setIf_over(config.getIf_over());
                    coupons.setMin_use_fee(config.getMin_use_fee());
                    coupons.setSentStatus(1);
                    coupons.setUseStaus(0);
                    coupons.setType(config.getType());
                    coupons.setCreateTime(new Date());
                    couponsRepository.save(coupons);
                }
            }

            return cardRepository.save(card);
        }
    }

    public Card update(Card card) {
        return cardRepository.save(card);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }

    public Card changeCardStatus(String cardNo,int cardStatus){
        Card card = cardRepository.findByCardNo(cardNo);
        if(card!=null){
            card.setCardStatus(cardStatus);
            return cardRepository.save(card);
        } else{
            return null;
        }
    }

    public void replace(String oldCardNo,String newCardNo){
        //将老卡置为 0：挂失
        Card oldCard = cardRepository.findByCardNo(oldCardNo);
        oldCard.setCardStatus(0);
        cardRepository.save(oldCard);

        //保存新卡信息,status为1：正常,shop为老卡的shopId
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(new Date());
        Card newCard = new Card();
        newCard.setCardNo(newCardNo);
        newCard.setCardStatus(1);
        newCard.setCardType(oldCard.getCardType());
        newCard.setCardCreateTime(createTime);
        newCard.setShopId(oldCard.getShopId());
        newCard.setUserId(oldCard.getUserId());
        newCard.setBalance(oldCard.getBalance());
        newCard.setJifen(oldCard.getJifen());
        cardRepository.save(newCard);
    }
}
