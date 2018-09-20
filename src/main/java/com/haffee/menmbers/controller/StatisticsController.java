package com.haffee.menmbers.controller;

import com.haffee.menmbers.entity.*;
import com.haffee.menmbers.service.StatisticsService;
import com.haffee.menmbers.service.UserService;
import com.haffee.menmbers.utils.ResponseMessage;
import com.haffee.menmbers.vo.ConsumeCountStatistics;
import com.haffee.menmbers.vo.ConsumeFeeStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;


/** 统计信息的总入口controller
 * create by liujia
 * date 2018/9/19 下午4:28
 **/

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Resource
    private StatisticsService statisticsService;

    /**
     * 获取用户总数
     * @param shopId
     * @return
     */
    @PostMapping("/getUserTotal")
    public ResponseMessage getUserTotal(int shopId) {
        try {
            String total = statisticsService.getUserTotal(shopId);
            if (null != total&&!"".equals(total)) {
                return ResponseMessage.success(total);
            } else {
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 获取充值总金额
     * @param shopId
     * @return
     */
    @PostMapping("/getRechargeFeeTotal")
    public ResponseMessage getRechargeFeeTotal(int shopId) {
        try {
            float fee = statisticsService.getRechargeFeeTotal(shopId);
            if (!"".equals(fee)) {
                return ResponseMessage.success(fee);
            } else {
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 获取当日消费
     * @param shopId
     * @return
     */
    @PostMapping("/getConsumeFeeToday")
    public ResponseMessage getConsumeFeeToday(int shopId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        try {
            float fee = statisticsService.getConsumeFeeToday(shopId,today);
            if (!"".equals(fee)) {
                return ResponseMessage.success(fee);
            } else {
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }

    /**
     * 获取消费金额排行列表
     * @param shopId
     * @return
     */
    @PostMapping("/getConsumeFeeList")
    public ResponseMessage getConsumeFeeList(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,int shopId) {
        try {
            if(page>0){
                page = page -1;
            }
            if(size==0){
                size = 10;
            }
            List<ConsumeFeeStatistics> pageBack = statisticsService.getConsumeFeeList(shopId,PageRequest.of(page,size));
            if (pageBack!=null) {
                return ResponseMessage.success(pageBack);
            } else {
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
    /**
     * 获取消费次数排行列表
     * @param shopId
     * @return
     */
    @PostMapping("/getConsumeCountList")
    public ResponseMessage getConsumeCountList(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,int shopId) {
        try {
            if(page>0){
                page = page -1;
            }
            if(size==0){
                size = 10;
            }
            List<ConsumeCountStatistics> pageBack = statisticsService.getConsumeCountList(shopId,PageRequest.of(page,size));
            if (pageBack!=null) {
                return ResponseMessage.success(pageBack);
            } else {
                return ResponseMessage.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.error();
        }
    }
//    /**
//     * 获取当日消费
//     * @param shopId
//     * @return
//     */
//    @PostMapping("/getConsumeFeeToday")
//    public ResponseMessage getConsumeFeeToday(int shopId) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String today = sdf.format(new Date());
//        try {
//            float fee = statisticsService.getConsumeFeeToday(shopId,today);
//            if (!"".equals(fee)) {
//                return ResponseMessage.success(fee);
//            } else {
//                return ResponseMessage.error();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseMessage.error();
//        }
//    }
}