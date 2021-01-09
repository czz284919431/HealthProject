package com.czz.dao;

import com.czz.health.pojo.OrderSetting;

import java.util.Date;

public interface OrderSettingDao {

    OrderSetting findOrderSetting(Date orderDate);

    void addOrderSetting(OrderSetting orderSetting);

    void updateOrderSetting(OrderSetting orderSetting);
}
