package com.czz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czz.dao.OrderSettingDao;
import com.czz.exception.MyException;
import com.czz.health.pojo.OrderSetting;
import com.czz.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author czz
 * @create 2021-01-09 17:54
 **/
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入数据
     *
     * @param orderSettingList
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (null != orderSettingList) {
            for (OrderSetting orderSetting : orderSettingList) {
                OrderSetting os = orderSettingDao.findOrderSetting(orderSetting.getOrderDate());
                if (null == os) {
                    //根据日期查找预约设置，为空时
                    orderSettingDao.addOrderSetting(orderSetting);
                } else {
                    //不为空时判断人数是否达上限
                    if (os.getNumber() > os.getReservations()) {
                        //更新数据
                        orderSettingDao.updateOrderSetting(orderSetting);
                    } else {
                        throw new MyException(dateFormat.format(orderSetting.getOrderDate()) + ":最大预约数不能小于已预约人数");
                    }
                }
            }
        }

    }
}
