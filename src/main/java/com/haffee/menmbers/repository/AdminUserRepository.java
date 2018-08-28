package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.AdminUser;
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
 * date 2018/7/17 下午7:39
 **/

public interface AdminUserRepository extends JpaRepository<AdminUser,Integer> {


    /**
     * 根据用户名及类型查询管理用户
     * @param userPhone
     * @param type
     * @return
     */
    @Query(value = "select * from admin_user where user_phone = ?1 and type = ?2",nativeQuery = true)
    AdminUser findAdminUser(String userPhone,String type);


    /**
     * 更新
     * @param loginKey
     * @param lastLoginTime
     * @param id
     * @return
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update admin_user set login_key = :loginkey , last_login_time = :lastLoginTime where id = :id",nativeQuery = true)
    int updateAdminUser(@Param("loginkey")String loginKey, @Param("lastLoginTime")Date lastLoginTime, @Param("id")int id);

    /**
     * 分页查询系统管理账户
     * @param type
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM admin_user WHERE type = ?1",
            countQuery = "SELECT count(*) FROM admin_user WHERE type = ?1",
            nativeQuery = true)
    Page<AdminUser> findAllByType(int type, Pageable pageable);


}
