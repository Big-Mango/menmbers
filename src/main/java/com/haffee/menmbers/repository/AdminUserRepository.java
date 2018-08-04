package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * @param userPhone
     * @param type
     * @return
     */
    @Query(value = "select * from AdminUser where userPhone = ?1 and type = ?2",nativeQuery = true)
    AdminUser findAdminUser(String userPhone,String type);


    /**
     * 更新
     * @param loginKey
     * @param lastLoginTime
     * @param id
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update AdminUser set loginKey = ?1 , lastLoginTime = ?2 where id = ?3")
    int updateAdminUser(String loginKey,Date lastLoginTime,int id);

    /**
     * 分页查询系统管理账户
     * @param type
     * @param pageable
     * @return
     */
    Page<AdminUser> findByType(int type, Pageable pageable);


}
