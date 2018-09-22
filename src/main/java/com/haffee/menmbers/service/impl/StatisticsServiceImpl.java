package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.repository.*;
import com.haffee.menmbers.service.StatisticsService;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.ConfigUtils;
import com.haffee.menmbers.utils.Md5Utils;
import com.haffee.menmbers.utils.SmsUtils;
import com.haffee.menmbers.utils.UuidUtils;
import com.haffee.menmbers.vo.ConsumeCountStatistics;
import com.haffee.menmbers.vo.ConsumeFeeStatistics;
import com.haffee.menmbers.vo.RechargeFeeStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by liujia
 * date 2018/9/20 上午10:01
 **/

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private UserRepository UserRepository;

    @Resource
    private CardRechargeRepository cardRechargeRepository;

    @Resource
    private CardConsumeRepository cardConsumeRepository;

    /**
     * 获取总人数
     * @param shopId
     * @return
     */
    @Override
    public String getUserTotal(int shopId){
        return UserRepository.getUserTotal(shopId);
    }

    /**
     * 获取充值总金额
     * @param shopId
     * @return
     */
    @Override
    public float getRechargeFeeTotal(int shopId){
        return cardRechargeRepository.getRechargeFeeTotal(shopId);
    }

    /**
     * 获取今日消费总金额
     * @param shopId
     * @param today 当前日期 格式为 yyyy-mm-dd
     * @return
     */
    @Override
    public float getConsumeFeeToday(int shopId,String today){
        return cardConsumeRepository.getConsumeFeeToday(shopId,today);
    }

    /**
     * 获取消费金额排行列表
     * @param shopId
     * @return
     */
    @Override
    public List<ConsumeFeeStatistics> getConsumeFeeList(int shopId, Pageable pageable){
        List<ConsumeFeeStatistics> list = new ArrayList();
        List<Object> dataList = cardConsumeRepository.getConsumeFeeList(shopId,pageable);
        for(Object obj : dataList){
            Object[] bean = (Object[]) obj;
            ConsumeFeeStatistics consumeFeeStatistics = new ConsumeFeeStatistics();
            consumeFeeStatistics.setPayFee((Double)bean[0]);
            consumeFeeStatistics.setUserPhone(bean[1].toString());
            consumeFeeStatistics.setRealName(bean[2].toString());
            list.add(consumeFeeStatistics);
        }
        return list;
    }

    /**
     * 获取消费次数排行列表
     * @param shopId
     * @return
     */
    @Override
    public List<ConsumeCountStatistics> getConsumeCountList(int shopId, Pageable pageable){
        List<ConsumeCountStatistics> list = new ArrayList();
        List<Object> dataList = cardConsumeRepository.getConsumeCountList(shopId,pageable);
        for(Object obj : dataList){
            Object[] bean = (Object[]) obj;
            ConsumeCountStatistics consumeCountStatistics = new ConsumeCountStatistics();
            consumeCountStatistics.setCount(bean[0].toString());
            consumeCountStatistics.setUserPhone(bean[1].toString());
            consumeCountStatistics.setRealName(bean[2].toString());
            list.add(consumeCountStatistics);
        }
        return list;
    }
    /**
     * 获取充值金额列表
     * @param shopId
     * @return
     */
    @Override
    public List<RechargeFeeStatistics> getRechargeFeeList(int shopId, Pageable pageable){
        List<RechargeFeeStatistics> list = new ArrayList();
        List<Object> dataList = cardRechargeRepository.getRechargeFeeList(shopId,pageable);
        for(Object obj : dataList){
            Object[] bean = (Object[]) obj;
            RechargeFeeStatistics rechargeFeeStatistics = new RechargeFeeStatistics();
            rechargeFeeStatistics.setPayFee((Double)bean[0]);
            rechargeFeeStatistics.setUserPhone(bean[1].toString());
            rechargeFeeStatistics.setRealName(bean[2].toString());
            list.add(rechargeFeeStatistics);
        }
        return list;
    }

}
