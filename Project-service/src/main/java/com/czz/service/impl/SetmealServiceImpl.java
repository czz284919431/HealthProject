package com.czz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czz.dao.SetmealDao;
import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.exception.MyException;
import com.czz.health.pojo.Setmeal;
import com.czz.service.SetmealService;
import com.czz.utils.QiNiuUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Integer add(Setmeal setmeal, Integer[] checkGroupIds) {
        Integer setmealId1 = setmealDao.addSetmeal(setmeal);
        Integer setmealId = setmeal.getId();
        if (null != setmealId) {
            for (Integer checkGroupId : checkGroupIds) {
                setmealDao.addCheckGroupBySetmealId(setmealId, checkGroupId);
            }
        }
        return setmealId1;
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

    /**
     * 查询套餐
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    /**
     * 根据套餐查询检查组
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupBySetmeal(int id) {
        List<Integer> integerList = setmealDao.findCheckGroupBySetmeal(id);
        return integerList;
    }

    /**
     * 编辑套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @throws MyException
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) throws MyException {
        Integer setmealId = setmeal.getId();
        setmealDao.update(setmeal);
        setmealDao.deleteCheckGroupBySetmeal(setmealId);

        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addCheckGroupBySetmealId(setmealId, checkgroupId);
            }
        }
    }

    /**
     * 删除
     *
     * @param id
     * @throws MyException
     */
    @Override
    @Transactional
    public void delete(int id) throws MyException {
        int count = setmealDao.findSetmealByOrder(id);
        if (count > 0) {
            throw new MyException("该套餐被订单使用了，不能删除");
        }
        setmealDao.deleteCheckGroupBySetmeal(id);
        setmealDao.deleteSetmeal(id);

    }

    /**
     * 获取套餐列表信息
     *
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> setmealList = setmealDao.findAll();
        return setmealList;
    }

    /**
     * 获取套餐详细信息（检查组、检查项）
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findDetailById(int id) {
        Setmeal setmeal = setmealDao.findDetailById(id);
        return setmeal;
    }
}
