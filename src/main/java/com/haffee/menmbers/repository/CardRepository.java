package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @Description:    java类作用描述
* @Author:         liujia
* @CreateDate:     2018/7/29 10:02
* @Version:        1.0
*/
public interface CardRepository extends JpaRepository<Card,Integer> {
    Card findByCardNo(String cardNo);

    /**
     * 根据用户ID查询名下会员卡 --add by jacktong 2018-9-5
     * @param user_id
     * @return
     */
    @Query(value = "select * from card where user_id = ?1",nativeQuery = true)
    List<Card> findCardByUserId(int user_id);

    /**
     * 根据userId,shopId查询会员卡信息 --add by liujia 2018-9-5
     * @param userId
     * @param shopId
     * @return
     */
    @Query(value = "select * from card where user_id = ?1 and shop_id = ?2 and card_status = 1",nativeQuery = true)
    Card findCardByUserIdShopId(int userId,int shopId);
}
