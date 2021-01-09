package com.czz.service;

import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    /**
     * 分页查询检查项组
     *
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 查询所有
     *
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 添加检查组
     *
     * @param checkitemIds
     * @param checkGroup
     */
    void add(Integer[] checkitemIds, CheckGroup checkGroup);

    /**
     * 查找检查组
     *
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据检查组id查询检查项id数组
     *
     * @param id
     * @return
     */
    List<Integer> findCheckItemByCheckGroup(Integer id);

    /**
     * 编辑
     *
     * @param checkitemIds
     * @param checkGroup
     */
    void update(Integer[] checkitemIds, CheckGroup checkGroup);
}
