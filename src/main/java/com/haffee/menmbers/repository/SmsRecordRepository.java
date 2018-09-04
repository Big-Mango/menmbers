package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.SmsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by jacktong
 * date 2018/7/17 下午7:45
 **/

public interface SmsRecordRepository extends JpaRepository<SmsRecord,Integer> {

    @Query(value = "select * from sms_record where status=1 and phone=?1 and valid_code = ?2 and createTime >= now()-interval 5 minute",nativeQuery = true)
    SmsRecord findOne(String phone,String code);

    @Query(value = "select * from sms_record where status=1 and phone=?1 ",nativeQuery = true)
    List<SmsRecord> findAll(String phone);
}
