package com.czz.service;

import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.health.pojo.Setmeal;

public interface SetmealService {

    void add(Setmeal setmeal, Integer[] checkGroupIds);

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

}
