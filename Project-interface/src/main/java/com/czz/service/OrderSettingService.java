package com.czz.service;

import com.czz.exception.MyException;
import com.czz.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    /**
     * 批量导入数据
     * @param orderSettingList
     * @throws MyException
     */
    void add(List<OrderSetting> orderSettingList) throws MyException;

    /**
     * 获取当前预约设置界面
     * @param month
     * @return
     */
    List<Map<String, Integer>> findByMonth(String month);

    /**
     * 设置可预约人数
     * @param orderSetting
     * @throws MyException
     */
    void updateNumber(OrderSetting orderSetting) throws MyException;

}
