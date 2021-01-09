package com.czz.dao;

import com.czz.health.pojo.CheckGroup;
import com.czz.health.pojo.CheckItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CheckGroupDao {

    /**
     * 分页查询检查项组
     *
     * @param queryString
     * @return
     */
    Page<CheckGroup> findPageByCondition(String queryString);

    /**
     * 查询所有
     *
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 检查组里添加检查项
     *
     * @param checkGroupId
     * @param checkitemId
     */
    void addCheckItemByGroupId(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    /**
     * 添加检查项
     *
     * @param checkGroup
     */
    void addGroup(CheckGroup checkGroup);

    /**
     * 查询检查组
     *
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据检查组id查询检查项id数组
     * @param id
     * @return
     */
    List<Integer> findCheckItemByCheckGroup(Integer id);


   /* void c(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);*/

    /**
     * 编辑
     * @param checkGroup
     */
    void updateCheckGroup(CheckGroup checkGroup);

    void deleteCheckItemByCheckGroup(Integer checkGroupId);
}
