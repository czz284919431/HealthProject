package com.czz.service.impl;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.czz.dao.SetmealDao;
import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.health.pojo.Setmeal;
import com.czz.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author czz
 * @create 2021-01-08 20:33
 **/
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    private PageResult<Setmeal> pageResult;

    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealDao.addSetmeal(setmeal);
        Integer setmealId = setmeal.getId();
        if (null != setmealId) {
            for (Integer checkGroupId : checkGroupIds) {
                setmealDao.addCheckGroupBySetmealId(setmealId, checkGroupId);
            }
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        if (queryPageBean.getPageSize() < 50) {
            //判断查询条件是否为空
            if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
                queryPageBean.setQueryString("%" + queryPageBean.getQueryString());
            }
            Page<Setmeal> setmealPage = setmealDao.findPage(queryPageBean.getQueryString());
            pageResult = new PageResult<>(setmealPage.getTotal(), setmealPage.getResult());
        }
        return pageResult;
    }
}
