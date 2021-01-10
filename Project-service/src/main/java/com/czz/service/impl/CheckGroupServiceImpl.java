package com.czz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czz.dao.CheckGroupDao;
import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.entity.Result;
import com.czz.exception.MyException;
import com.czz.health.pojo.CheckGroup;
import com.czz.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author czz
 * @create 2021-01-07 16:31
 **/
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    PageResult<CheckGroup> pageResult = null;

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {

        //分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //判断当前页的大小
        if (queryPageBean.getPageSize() <= 50) {
            //当查询条件不为空时
            if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
                queryPageBean.setQueryString("%" + queryPageBean.getQueryString());
            }

            //模糊查询,Page实际上也是继承了list
            Page<CheckGroup> page = checkGroupDao.findPageByCondition(queryPageBean.getQueryString());
            pageResult = new PageResult<CheckGroup>(page.getTotal(), page.getResult());

        }
        return pageResult;
    }

    /**
     * 查询所有检查组
     *
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> checkGroupList = checkGroupDao.findAll();
        return checkGroupList;
    }

    /**
     * 添加检查组
     *
     * @param checkitemIds
     * @param checkGroup
     */
    @Override
    public void add(Integer[] checkitemIds, CheckGroup checkGroup) {
        checkGroupDao.addGroup(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        if (null != checkGroupId) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckItemByGroupId(checkGroupId, checkitemId);
            }
        }
    }

    /**
     * 查找检查组
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }

    /**
     * 根据检查组id查询检查项id数组
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemByCheckGroup(Integer id) {
        List<Integer> integerList = checkGroupDao.findCheckItemByCheckGroup(id);
        return integerList;
    }

    /**
     * 编辑
     *
     * @param checkitemIds
     * @param checkGroup
     */
    @Override
    @Transactional
    public void update(Integer[] checkitemIds, CheckGroup checkGroup) {
        // 编辑检查组
        checkGroupDao.updateCheckGroup(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        checkGroupDao.deleteCheckItemByCheckGroup(checkGroupId);
        if (null != checkitemIds) {
            for (Integer checkItemId : checkitemIds) {
                checkGroupDao.addCheckItemByGroupId(checkGroup.getId(), checkItemId);
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
    public void delete(int id) throws MyException {
        int count = checkGroupDao.findCheckGroupBySetmeal(id);
        if (count > 0) {
            throw new MyException("有关联套餐，不能删除");
        }
        checkGroupDao.delete(id);
    }
}
