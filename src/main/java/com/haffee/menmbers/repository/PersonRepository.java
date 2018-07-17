package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by jacktong
 * date 2018/7/17 下午7:44
 **/

public interface PersonRepository extends JpaRepository<Person,Long> {
}
