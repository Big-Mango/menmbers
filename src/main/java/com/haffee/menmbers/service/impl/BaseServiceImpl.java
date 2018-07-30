package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.SysCode;
import com.haffee.menmbers.repository.SysCodeRepository;
import com.haffee.menmbers.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
