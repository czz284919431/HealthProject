package com.czz.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.czz.constant.MessageConstant;
import com.czz.constant.RedisMessageConstant;
import com.czz.entity.Result;
import com.czz.health.pojo.Member;
import com.czz.service.MemberService;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author czz
 * @create 2021-01-14 17:32
 **/
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @PostMapping("/check")
    public Result check(@RequestBody Map<String, String> loginInfo, HttpServletResponse response) {
        String telephone = loginInfo.get("telephone");
        //校验验证码是否正确
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        Jedis jedis = jedisPool.getResource();
        String validateCode = jedis.get(key);
        //jedis内没有验证码
        if (StringUtil.isEmpty(validateCode)) {
            return new Result(false, "请重新发送验证码");
        }
        //判断验证码存在但不相等时
        if (!loginInfo.get("validateCode").equals(validateCode)) {
            return new Result(false, "验证码不正确");
        }
        //验证码相等时则jedis删除key
        jedis.del(key);
        //判断是否时新会员
        Member member = memberService.findMemberByTel(telephone);
        if (null == member) {
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            memberService.add(member);
        }
        member.setRemark("手机快速登录");
        //追踪cookie
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        //添加cookie存活时间
        cookie.setMaxAge(30 * 24 * 60 * 60);
        //cookie存在的路径
        cookie.setPath("/");
        //cookie响应给服务
        response.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
