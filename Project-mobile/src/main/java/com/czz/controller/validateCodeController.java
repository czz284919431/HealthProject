package com.czz.controller;

import com.aliyuncs.exceptions.ClientException;
import com.czz.constant.MessageConstant;
import com.czz.constant.RedisMessageConstant;
import com.czz.entity.Result;
import com.czz.utils.SMSUtils;
import com.czz.utils.ValidateCodeUtils;
import com.github.pagehelper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 验证码校验
 *
 * @author czz
 * @create 2021-01-12 17:25
 **/
@RestController
@RequestMapping("/validateCode")
public class validateCodeController {

    @Autowired
    private JedisPool jedisPool;

    private static final Logger log = LoggerFactory.getLogger(validateCodeController.class);

    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
        //判断Redis内有无验证码
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        if (jedis.exists(key)) {
            return new Result(false, "验证码发送过了，请注意查看");
        }
        //没有则生成验证码
        String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
        log.debug("==========生成的验证码为：{}", validateCode);
        //发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode);
        } catch (ClientException e) {
            //e.printStackTrace();
            log.debug("==========手机号{}发送的验证码{}失败", telephone, validateCode);
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //设置验证码有效期
        jedis.setex(key,10*60,validateCode+"");
        jedis.close();
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        //判断Redis内有无验证码
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        if (jedis.exists(key)) {
            return new Result(false, "验证码发送过了，请注意查看");
        }
        //没有则生成验证码
        String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
        log.debug("==========生成的验证码为：{}", validateCode);
        //发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode);
        } catch (ClientException e) {
            //e.printStackTrace();
            log.debug("==========手机号{}发送的验证码{}失败", telephone, validateCode);
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //设置验证码有效期
        jedis.setex(key,10*60,validateCode+"");
        jedis.close();
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
