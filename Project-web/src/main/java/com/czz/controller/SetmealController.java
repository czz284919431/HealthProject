package com.czz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czz.constant.MessageConstant;
import com.czz.entity.PageResult;
import com.czz.entity.QueryPageBean;
import com.czz.entity.Result;
import com.czz.health.pojo.Setmeal;
import com.czz.service.SetmealService;
import com.czz.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author czz
 * @create 2021-01-08 17:15
 **/
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static Logger log = LoggerFactory.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    /**
     * 上传图片
     *
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {

        //获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        //截取
        String substring = originalFilename.substring(originalFilename.indexOf("."));
        //UUID定义单一值
        String uuid = UUID.randomUUID().toString();
        //设置文件名
        String fileName = uuid + substring;

        try {
            //调用工具类上传图片
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), fileName);
            //图片上传完后分两部分进行回显
            Map<String, String> map = new HashMap<>();
            map.put("fileName", fileName);
            //domain为七牛云上的地址
            map.put("domain", QiNiuUtils.DOMAIN);
            System.out.println(map);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (Exception e) {
            log.error("上传图片失败");
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 添加
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setmealService.add(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }
}