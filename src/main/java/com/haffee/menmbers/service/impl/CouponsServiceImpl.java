package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.Coupons;
import com.haffee.menmbers.entity.Shop;
import com.haffee.menmbers.repository.CouponsRepository;
import com.haffee.menmbers.repository.ShopRepository;
import com.haffee.menmbers.service.CouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * create by jacktong
 * date 2018/9/28 下午9:14
 **/

@Service
@Transactional
public class CouponsServiceImpl implements CouponsService {

    @Autowired
    private CouponsRepository couponsRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public List<Coupons> findEnableCouponsByUserAndShop(int shop_id, int user_id,float order_fee) {
        return couponsRepository.findEnableCouponsByUserAndShop(user_id,shop_id,order_fee);
    }

    @Override
    public List<Coupons> findAllCouponsByUser(int user_id) {
        List<Coupons> list = couponsRepository.findAllCouponsByUser(user_id);
        if(list.size()>0){
            for (Coupons c:list) {
                Optional<Shop> o = shopRepository.findById(c.getId());
                if(o.isPresent()){
                    c.setShop(o.get());
                }
            }
        }
        return list;
    }
}
