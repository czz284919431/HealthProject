package com.czz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czz.constant.MessageConstant;
import com.czz.entity.Result;
import com.czz.health.pojo.Setmeal;
import com.czz.service.SetmealService;
import com.czz.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author czz
 * @create 2021-01-11 10:32
 **/
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    /**
     * 获取所有套餐
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result findAll() {
        List<Setmeal> setmealList = setmealService.findAll();
        setmealList.forEach(s -> s.setImg(QiNiuUtils.DOMAIN + s.getImg()));
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmealList);
    }

    @GetMapping("/findDetailById")
    public Result findDetailById(int id) {
        Setmeal setmeal = setmealService.findDetailById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmeal);
    }

    /**
     * 查询套餐
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result order(int id) {
        Setmeal setmeal = setmealService.findById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
}
