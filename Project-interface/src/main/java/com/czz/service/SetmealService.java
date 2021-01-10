package com.czz.service;

import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.exception.MyException;
import com.czz.health.pojo.CheckGroup;
import com.czz.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    void add(Setmeal setmeal, Integer[] checkGroupIds) throws MyException;

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    Setmeal findById(int id);

    List<Integer> findCheckGroupBySetmeal(int id);

    void update(Setmeal setmeal, Integer[] checkgroupIds) throws MyException;

    void delete(int id) throws MyException;
}
