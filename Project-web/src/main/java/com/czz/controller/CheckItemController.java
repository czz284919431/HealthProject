package com.czz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czz.constant.MessageConstant;
import com.czz.entity.Result;
import com.czz.health.pojo.CheckItem;
import com.czz.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author czz
 * @create 2021-01-05 17:59
 **/
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/findAll")
    public Result findAll() {
        List<CheckItem> checkItemList = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkItemList);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS,checkItem);
    }
}
