package com.czz.service;

import com.czz.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    List<CheckItem> findAll();

    void add(CheckItem checkItem);

}
