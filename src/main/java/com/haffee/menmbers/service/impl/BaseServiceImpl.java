package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.SmsRecord;
import com.haffee.menmbers.entity.SysCode;
import com.haffee.menmbers.repository.SmsRecordRepository;
import com.haffee.menmbers.repository.SysCodeRepository;
import com.haffee.menmbers.service.BaseService;
import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * create by jacktong
 * date 2018/7/26 下午6:30
 **/

@Service
@Transactional
public class BaseServiceImpl implements BaseService {

    @Autowired
    private SysCodeRepository sysCodeRepository;

    @Autowired
    private SmsRecordRepository smsRecordRepository;

    /**
     * 根据code查询二级代码
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public List<SysCode> selectByCode(String code){
        return sysCodeRepository.selectbyCode(code);
    }

    /**
     * 发送验证码
     * @param phone
     */
    @Override
    public void sendCheckCode(String phone) {
        System.out.println("验证码手机号："+phone);
        StringBuffer sms_content = new StringBuffer();
        String sms_content_template = ConfigUtils.getCheck_code();
        int code = (int)((Math.random()*9+1)*100000);
        if(null!=sms_content_template){
            //拼接短信内容
            String [] a = sms_content_template.split("&");
            sms_content.append(a[0]+code+a[1]);
            //发送验证码，将之前有效的验证码置无效
            List<SmsRecord> sr_list = smsRecordRepository.findAll(phone);
            for (SmsRecord s:sr_list){
                s.setStatus(0);
                smsRecordRepository.save(s);
            }
            //存记录
            SmsRecord sr = new SmsRecord();
            sr.setPhone(phone);
            sr.setValidCode(code+"");
            sr.setStatus(1);
            sr.setCreateTime(new Date());
            smsRecordRepository.save(sr);
            //发送
            SmsUtils.singleSend(phone,sms_content.toString());
            System.out.println("短信内容："+sms_content.toString());
        }
    }

    /**
     * 校验验证码
     * @param phone
     * @param code
     * @return
     */
    @Override
    public boolean doCheckCode(String phone, String code) {
        boolean isSuccess = false;
        SmsRecord sr = smsRecordRepository.findOne(phone,code);
        if(null!=sr){
            isSuccess = true;
        }
        return isSuccess;
    }
}
