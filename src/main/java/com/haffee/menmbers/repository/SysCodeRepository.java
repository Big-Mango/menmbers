package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.SysCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by jacktong
 * date 2018/7/17 下午7:46
 **/

public interface SysCodeRepository extends JpaRepository<SysCode,Long> {

    /**
     * 根据code查询
     * @param code
     * @return
     */
    @Query(value="select * from SysCode where code = ?1")
    List<SysCode> findbyCode(String code);
}
