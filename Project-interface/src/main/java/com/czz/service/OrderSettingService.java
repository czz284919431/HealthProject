package com.czz.service;

import com.czz.exception.MyException;
import com.czz.health.pojo.OrderSetting;

import java.util.List;

public interface OrderSettingService {

    void add(List<OrderSetting> orderSettingList) throws MyException;
}
