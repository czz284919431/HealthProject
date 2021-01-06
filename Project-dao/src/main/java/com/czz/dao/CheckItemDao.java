package com.czz.dao;

import com.czz.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    List<CheckItem> findAll();

    void add(CheckItem checkItem);
}
