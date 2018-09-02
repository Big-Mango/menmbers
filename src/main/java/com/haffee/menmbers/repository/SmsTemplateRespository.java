package com.haffee.menmbers.repository;

import com.haffee.menmbers.entity.SmsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * create by jacktong
 * date 2018/9/2 下午1:16
 **/

public interface SmsTemplateRespository extends JpaRepository<SmsTemplate,Integer> {

    /**
     * 查询短信模板
     * @param code
     * @return
     */
    @Query(value = "select * from sms_template where template_code = ?1 and status = 1",nativeQuery = true)
    SmsTemplate findOneByCode(String code);
}
