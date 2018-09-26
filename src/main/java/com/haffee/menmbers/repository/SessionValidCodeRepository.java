package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.SessionValidCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * create by jacktong
 * date 2018/9/26 下午8:42
 **/

public interface SessionValidCodeRepository extends JpaRepository<SessionValidCode,Integer> {

    @Query(value = "select * from session_valid_code where session_id=?1",nativeQuery = true)
    SessionValidCode findBySessionID(String sessionid);


}
