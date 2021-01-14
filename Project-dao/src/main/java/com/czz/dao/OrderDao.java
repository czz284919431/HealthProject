package com.czz.dao;

import com.czz.health.pojo.Order;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    /**
     * 添加预约订单
     * @param order
     */
    void add(Order order);

    /**
     * 查询预约订单
     * @param order
     * @return
     */
    List<Order> findOrderByOrder(Order order);

    /**
     *
     * 获取预约信息
     * @param id
     * @return
     */
    Map<String,String> findById(int id);
}
