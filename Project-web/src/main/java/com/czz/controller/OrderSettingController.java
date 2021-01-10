package com.czz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czz.constant.MessageConstant;
import com.czz.entity.Result;
import com.czz.health.pojo.OrderSetting;
import com.czz.service.OrderSettingService;
import com.czz.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author czz
 * @create 2021-01-09 17:31
 **/
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    private static Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    @Reference
    private OrderSettingService orderSettingService;

    private OrderSetting os;

    /**
     * 批量获取表格数据
     *
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(POIUtils.DATE_FORMAT);
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            log.info("上传的文件有" + excelFile.getSize() + "条");
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for (String[] string : strings) {
                Date date = dateFormat.parse(string[0]);
                int number = Integer.valueOf(string[1]);
                os = new OrderSetting(date, number);
                orderSettingList.add(os);
            }
            orderSettingService.add(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            log.error("导入预约设置失败", e);
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    /**
     * 获取当前预约设置界面
     *
     * @param month
     * @return
     */
    @GetMapping("/findByMonth")
    public Result findDataByMonth(String month) {
        List<Map<String, Integer>> mapList = orderSettingService.findByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, mapList);
    }

    /**
     * 更新预约设置人数
     *
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody OrderSetting orderSetting) {
        orderSettingService.updateNumber(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
