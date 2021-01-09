package com.czz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czz.dao.CheckItemDao;
import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.exception.MyException;
import com.czz.health.pojo.CheckItem;
import com.czz.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author czz
 * @create 2021-01-05 17:56
 **/
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    PageResult<CheckItem> pageResult = null;

    public List<CheckItem> findAll() {

        List<CheckItem> checkItemList = checkItemDao.findAll();
        return checkItemList;
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {

        //分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //判断当前页的大小
        if (queryPageBean.getPageSize() <= 50) {
            //当查询条件不为空时
            if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
                queryPageBean.setQueryString("%" + queryPageBean.getQueryString());
            }

            //模糊查询,Page实际上也是继承了list
            Page<CheckItem> page = checkItemDao.findPageByCondition(queryPageBean.getQueryString());
            pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());

        }
        return pageResult;
    }

    @Override
    public CheckItem findById(int id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public void delete(int id) throws MyException {
        int checkItemIds = checkItemDao.findCheckGroupByCheckItemId(id);
        if (checkItemIds > 0) {
            throw new MyException("此检查组绑定有绑定检查项，不能删除");
        }
        checkItemDao.delete(id);
    }

}
