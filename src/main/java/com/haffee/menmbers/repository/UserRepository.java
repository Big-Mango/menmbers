package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;


/**
 * create by jacktong
 * date ${date}
 **/
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 根据手机号查询用户
     * @param user_phone
     * @return
     */
    @Query(value="select u from User u where user_phone = ?1")
    User findUserByUser_phone(String user_phone);


    /**
     * 登录更新
     * @param login_key
     * @param last_login_time
     * @param id
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query(value="update User set login_key = ?1 , last_login_time = ?2 where id = ?3")
    int updateUser(String login_key, Date last_login_time, int id);

}
