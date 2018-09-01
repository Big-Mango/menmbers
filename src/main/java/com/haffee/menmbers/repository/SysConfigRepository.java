package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * create by jacktong
 * date 2018/9/1 下午12:46
 **/

public interface SysConfigRepository extends JpaRepository<SysConfig,Integer> {


    /**
     * 根据参数名称查询配置信息
     * @param param
     * @return
     */
    @Query(value = "select * from sys_config where param_name = ?1 and status = 1",nativeQuery = true)
    SysConfig selectByKeyParam(String param);
}
