package com.czz.dao;

import com.czz.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

    OrderSetting findOrderSetting(Date orderDate);

    void addOrderSetting(OrderSetting orderSetting);

    void updateOrderSetting(OrderSetting orderSetting);

    List<Map<String,Integer>> findByMonth(String month);

    void updateNumber(OrderSetting orderSetting);

    /**
     * 根据预约日期查找预约设置
     * @param orderDate
     * @return
     */
    OrderSetting findOrderDate(Date orderDate);


    /**
     * 更新已预约人数
     * @param orderSetting
     */
    void updateReservationsByOrderSetting(OrderSetting orderSetting);
}
