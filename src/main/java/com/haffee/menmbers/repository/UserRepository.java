package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.AdminUser;
import com.haffee.menmbers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * create by jacktong
 * date  2018/7/17 下午7:46
 **/
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 根据手机号查询用户
     *
     * @param user_phone
     * @return
     */
    @Query(value = "select * from user u where u.user_phone = ?1", nativeQuery = true)
    User findByUserPhone(String user_phone);

    /**
     * 根据手机号或者卡号查询用户
     *
     * @param user_phone
     * @param shop_id
     * @return
     */
    @Query(value = "select * from user u where (u.user_phone = ?1 and exists(select 1 from card c where c.shop_id = ?2 and c.user_id=u.id and c.card_status=1 )) or (exists(select 1 from card b where b.user_id=u.id and b.shop_id=?2 and b.card_no = ?1))", nativeQuery = true)
    User findByUserPhoneOrCardNo(String user_phone, int shop_id);


    /**
     * 登录更新
     *
     * @param loginKey
     * @param lastLoginTime
     * @param id
     * @return
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update user set login_key = :loginKey , last_login_time = :lastLoginTime where id = :id", nativeQuery = true)
    int updateUser(@Param("loginKey") String loginKey, @Param("lastLoginTime") Date lastLoginTime, @Param("id") int id);

    /**
     * 根据卡号查询用户
     *
     * @param cardNo
     * @return
     */
    @Query(value = "select u from User u where exists (select 1 from Card c where u.id = c.userId and c.cardNo = ?1)")
    User getUserByCardNo(String cardNo);

    /**
     * 根据手机号判断用户是否已经是该商户下的会员
     *
     * @param userPhone
     * @param shopId
     * @return
     */
    @Query(value = "select u from User u where userPhone = ?1 and exists (select 1 from Card c where u.id = c.userId and c.shopId = ?2 and c.cardStatus = 1)")
    User getUserByUserPhoneShopId(String userPhone, int shopId);

    /**
     * 根据手机号查询用户
     *
     * @param userPhone
     * @return
     */
    @Query(value = "select u from User u where u.userPhone = ?1")
    Page<User> findAllByUserPhone(Pageable pageable, String userPhone);


    /**
     * 查询当前商户的所有用户
     *
     * @param pageable
     * @param pageable
     * @return
     */
    @Query(value = "select * from user u where exists (select 1 from card c where u.id = c.user_id and c.shop_id = ?1 and c.card_status = 1)", nativeQuery = true)
    Page<User> findAllUser(Pageable pageable, int shopId);

    @Query(value = "select * from user u where u.user_phone like CONCAT('%',?2,'%') and exists (select 1 from card c where u.id = c.user_id and c.shop_id = ?1 and c.card_status = 1)", nativeQuery = true)
    Page<User> findAllUserByPhone(Pageable pageable, int shopId, String userPhone);

    @Query(value = "select * from user u where " +
            "exists (select 1 from card c where u.id = c.user_id and c.shop_id = ?1 and c.card_status = 1) and " +
            "exists(select 1 from person p where p.id = u.person_id and p.real_name like CONCAT('%',?2,'%'))", nativeQuery = true)
    Page<User> findAllUserByRealName(Pageable pageable, int shopId, String realName);

    @Query(value = "select * from user u where u.user_phone like CONCAT('%',?2,'%') and " +
            "exists (select 1 from card c where u.id = c.user_id and c.shop_id = ?1 and c.card_status = 1) and " +
            "exists(select 1 from person p where p.id = u.person_id and p.real_name like CONCAT('%',?3,'%'))", nativeQuery = true)
    Page<User> findAllUserByUserPhoneAndRealName(Pageable pageable, int shopId,String userPhone, String realName);


    /**
     * 查询当前商户的用户总数
     *
     * @param shopId
     * @return
     */
    @Query(value = "select count(1) count from user u where exists (select 1 from card c where u.id = c.user_id and c.shop_id = ?1)", nativeQuery = true)
    String getUserTotal(int shopId);
}
