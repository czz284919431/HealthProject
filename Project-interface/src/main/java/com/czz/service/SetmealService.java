package com.czz.service;

import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.exception.MyException;
import com.czz.health.pojo.CheckGroup;
import com.czz.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    Integer add(Setmeal setmeal, Integer[] checkGroupIds) throws MyException;

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    Setmeal findById(int id);

    List<Integer> findCheckGroupBySetmeal(int id);

    void update(Setmeal setmeal, Integer[] checkgroupIds) throws MyException;

    void delete(int id) throws MyException;

    /**
     * 获取套餐列表信息
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 获取套餐详细信息（检查组、检查项）
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);
}
