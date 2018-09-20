package com.haffee.menmbers.service;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.vo.ConsumeCountStatistics;
import com.haffee.menmbers.vo.ConsumeFeeStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * create by liujia
 * date 2018/9/20 上午10:01
 **/
public interface StatisticsService {

    String getUserTotal(int shopId);

    float getRechargeFeeTotal(int shopId);

    float getConsumeFeeToday(int shopId,String today);

    List<ConsumeFeeStatistics> getConsumeFeeList(int shopId, Pageable pageable);

    List<ConsumeCountStatistics> getConsumeCountList(int shopId, Pageable pageable);

//    Page<CardRecharge> getRechargeFeeList(int shopId);
}