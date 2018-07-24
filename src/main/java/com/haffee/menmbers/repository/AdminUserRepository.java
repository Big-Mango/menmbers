package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * create by jacktong
 * date 2018/7/17 下午7:39
 **/

public interface AdminUserRepository extends JpaRepository<AdminUser,Long> {


    /**
     * 根据用户名及类型查询管理用户
     * @param user_phone
     * @param type
     * @return
     */
    @Query(value = "select * from AdminUser where user_phone = ?1 and type = ?2",nativeQuery = true)
    AdminUser findAdminUser(String user_phone,String type);


    /**
     * 更新
     * @param login_key
     * @param last_login_time
     * @param id
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update AdminUser set login_key = ?1 , last_login_time = ?2 where id = ?3")
    int updateAdminUser(String login_key,Date last_login_time,int id);

}
