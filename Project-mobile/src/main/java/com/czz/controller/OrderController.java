package com.czz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czz.constant.MessageConstant;
import com.czz.constant.RedisMessageConstant;
import com.czz.entity.Result;
import com.czz.health.pojo.Order;
import com.czz.service.OrderService;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 预约 订单
 *
 * @author czz
 * @create 2021-01-12 15:57
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> orderInfo) {
        //校验验证码是否正确
        String telephone = orderInfo.get("telephone");
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        Jedis jedis = jedisPool.getResource();
        String validateCode = jedis.get(key);
        //jedis内没有验证码
        if (StringUtil.isEmpty(validateCode)) {
            return new Result(false, "请重新发送证码");
        }
        //判断验证码存在但不相等时
        if (!orderInfo.get("validateCode").equals(validateCode)) {
            return new Result(false, "验证码不正确");
        }
        //验证码相等时则jedis删除key
        jedis.del(key);
        Order order = orderService.submitOrder(orderInfo);
        return new Result(true, "获取订单成功", order);
    }

    @GetMapping("/findById")
    public Result findById(int id) {
        Map<String, String> orderInfoMap = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, orderInfoMap);
    }
}
