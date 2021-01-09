package com.czz.dao;

import com.czz.health.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface SetmealDao {

    void addSetmeal(Setmeal setmeal);

    void addCheckGroupBySetmealId(@Param("setmealId") Integer setmealId, @Param("checkGroupId") Integer checkGroupId);

    Page<Setmeal> findPage(String queryString);
}
