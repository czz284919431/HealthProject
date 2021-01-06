package com.czz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czz.dao.CheckItemDao;
import com.czz.health.pojo.CheckItem;
import com.czz.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author czz
 * @create 2021-01-05 17:56
 **/
@Service(interfaceClass = CheckItemService.class)
public class CheckItemImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    public List<CheckItem> findAll() {
        List<CheckItem> checkItemList = checkItemDao.findAll();
        return checkItemList;
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
}
