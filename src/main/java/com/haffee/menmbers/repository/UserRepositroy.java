package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * create by jacktong
 * date ${date}
 **/
public interface UserRepositroy extends JpaRepository<User,Long> {

}