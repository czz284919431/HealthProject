package com.czz.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czz.health.pojo.Setmeal;
import com.czz.service.SetmealService;
import com.czz.utils.QiNiuUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.awt.image.renderable.RenderableImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author czz
 * @create 2021-01-11 20:16
 **/
@Component
public class GenerateStaticHtml {

    @Value("${out_put_path}")
    private String out_put_path;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private Configuration configuration;

    @Reference
    private SetmealService setmealService;

    @PostConstruct
    public void init() {
        //引入freemarker初始化编码、加载模版路径
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(GenerateStaticHtml.class, "/ftl");

    }

    @Scheduled(initialDelay = 3000, fixedDelay = 1800000)
    public void generateStaticList() throws Exception {
        //设置redis key值
        Jedis jedis = jedisPool.getResource();
        //遍历元素
        String key = "setmeal:static:html";
        Set<String> setmealIds = jedis.zrange(key, 0, -1);
        if (!CollectionUtils.isEmpty(setmealIds)) {
            for (String setmealIdStr : setmealIds) {
                String[] setmealArr = setmealIdStr.split("\\|");
                String setmealId = setmealArr[0];//套餐id
                String operation = setmealArr[1];//操作
                if ("0".equals(operation)) {
                    File file = new File(out_put_path, "setmeal_" + setmealId + ".html");
                    if (file.exists()) {
                        file.delete();
                    }
                    System.out.println("删除页面");
                } else if ("1".equals(operation)) {
                    generateSetmealDetailHtml(setmealId);
                    System.out.println("添加页面" + setmealId);
                }
                jedis.zrem(key, setmealIdStr);
            }

                generateSetmealList();
                System.out.println("静态页面生成");
        }
        jedis.close();
    }

    /**
     * 生成套餐详情页面
     *
     * @param setmealId
     */
    private void generateSetmealDetailHtml(String setmealId) throws Exception {
        Setmeal setmeal = setmealService.findDetailById(Integer.parseInt(setmealId));
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        //构建map数据，目的传参给前端
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("setmeal", setmeal);
        //文件名
        String filename = out_put_path + "setmeal_" + setmealId + ".html";
        String templateName = "mobile_setmeal_detail.ftl";
        generateStaticHtml(dataMap, filename, templateName);
    }


    /**
     * 生成套餐模版
     *
     * @throws IOException
     * @throws TemplateException
     */
    private void generateSetmealList() throws Exception {
        //获取所有套餐信息
        List<Setmeal> setmealList = setmealService.findAll();
        //补充图片名字
        setmealList.forEach(s -> s.setImg(QiNiuUtils.DOMAIN + s.getImg()));
        //构建map数据，目的传参给前端
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("setmealList", setmealList);
        //构建输出流
        String filename = out_put_path + "mobile_setmeal.html";
        generateStaticHtml(dataMap, filename, "mobile_setmeal.ftl");
    }

    /**
     * 生成套餐通用页面模版
     *
     * @param map
     * @param fileName
     * @param tempName
     * @throws Exception
     */
    private void generateStaticHtml(Map<String, Object> map, String fileName, String tempName) throws Exception {
        //获取模版
        Template template = configuration.getTemplate(tempName);
        //构建输出流
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
        //流导入模版，导出文件
        template.process(map, writer);
        //关闭流
        writer.flush();
        writer.close();
    }
}
