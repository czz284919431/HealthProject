package com.czz.service;

import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.exception.MyException;
import com.czz.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    CheckItem findById(int id);

    void update(CheckItem checkItem);

    void delete(int id) throws MyException;
}
