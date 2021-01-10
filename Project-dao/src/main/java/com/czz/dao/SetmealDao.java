package com.czz.dao;

import com.czz.health.pojo.CheckGroup;
import com.czz.health.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface SetmealDao {

    void addSetmeal(Setmeal setmeal);

    void addCheckGroupBySetmealId(@Param("setmealId") Integer setmealId, @Param("checkGroupId") Integer checkGroupId);

    Page<Setmeal> findPage(String queryString);

    Setmeal findById(int id);

    List<Integer> findCheckGroupBySetmeal(int id);

    void update(Setmeal setmeal);


    void deleteCheckGroupBySetmeal(Integer setmealId);

    void deleteSetmeal(int id);

    int findSetmealByOrder(int id);
}
