package com.czz.service;

import com.czz.health.pojo.Order;

import java.util.Map;

public interface OrderService {

    Order submitOrder(Map<String, String> orderInfo);

    /**
     * 查询预约信息
     *
     * @param id
     * @return
     */
    Map<String, String> findById(int id);

}
