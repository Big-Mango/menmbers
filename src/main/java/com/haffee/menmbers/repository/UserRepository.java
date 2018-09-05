package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;


/**
 * create by jacktong
 * date  2018/7/17 下午7:46
 **/
public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * 根据手机号查询用户
     * @param user_phone
     * @return
     */
    @Query(value="select u from User u where u.userPhone = ?1")
    User findByUserPhone(String user_phone);


    /**
     * 登录更新
     * @param loginKey
     * @param lastLoginTime
     * @param id
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query(value="update User set loginKey = ?1 , lastLoginTime = ?2 where id = ?3")
    int updateUser(String loginKey, Date lastLoginTime, int id);

    /**
     * 根据卡号查询用户
     * @param cardNo
     * @return
     */
    @Query(value="select u from User u where exists (select 1 from Card c where u.id = c.userId and c.cardNo = ?1)")
    User getUserByCardNo(String cardNo);

    /**
     * 根据手机号查询用户
     * @param userPhone
     * @return
     */
    @Query(value="select u from User u where u.userPhone = ?1")
    Page<User> findAllByUserPhone(Pageable pageable,String userPhone);


    /**
     * 查询当前商户的所有用户
     * @param pageable
     * @param pageable
     * @return
     */
    @Query(value = "select * from user u where exists (select 1 from card c where u.id = c.user_id and c.shop_id = ?1 and c.card_status = 1)",nativeQuery = true)
    Page<User> findAllUser(Pageable pageable,int shopId);

}
