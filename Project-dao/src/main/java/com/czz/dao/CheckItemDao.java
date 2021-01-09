package com.czz.dao;

import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.health.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface CheckItemDao {

    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    Page<CheckItem> findPageByCondition(String queryString);

    CheckItem findById(int id);

    void update(CheckItem checkItem);

    void delete(int id);

   int findCheckGroupByCheckItemId(int id);
}
