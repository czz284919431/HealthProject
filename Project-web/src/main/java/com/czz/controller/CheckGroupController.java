package com.czz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czz.constant.MessageConstant;
import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.entity.Result;
import com.czz.health.pojo.CheckGroup;
import com.czz.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author czz
 * @create 2021-01-07 16:26
 **/
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 检查组分页查询
     *
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 查询所有检查组
     *
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupList);
    }

    /**
     * 添加检查组
     *
     * @param checkGroup
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
        checkGroupService.add(checkitemIds, checkGroup);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 查询检查组
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
    }

    /**
     * 根据检查组id查询检查项id数组
     *
     * @param id
     * @return
     */
    @GetMapping("/findCheckItemByCheckGroup")
    public Result findCheckItemByCheckGroup(int id) {
        List<Integer> integerList = checkGroupService.findCheckItemByCheckGroup(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, integerList);
    }

    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupService.update(checkitemIds,checkGroup);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
}
