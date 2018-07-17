package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.SmsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by jacktong
 * date 2018/7/17 下午7:45
 **/

public interface SmsRecordRepository extends JpaRepository<SmsRecord,Long> {
}
