package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.GiftCard;
import com.haffee.menmbers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description:    礼品卡管理
 * @Author:         liujia
 * @CreateDate:     2018/9/13 8:15
 * @Version:        1.0
 */
public interface GiftCardRepository extends JpaRepository<GiftCard,Integer> {
    GiftCard findByCardNo(String cardNo);

    /**
     * 根据店铺ID查询礼品卡
     * @param shopId
     * @return
     */
    @Query(value = "select * from gift_card where shop_id = ?1 and status = ?2",nativeQuery = true)
    Page<GiftCard> findAllByShopId(Pageable pageable,int shopId,int status);
}
