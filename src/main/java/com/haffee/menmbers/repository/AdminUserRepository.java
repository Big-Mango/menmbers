package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by jacktong
 * date 2018/7/17 下午7:39
 **/

public interface AdminUserRepository extends JpaRepository<AdminUser,Long> {
}
