package com.czz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czz.dao.MemberDao;
import com.czz.dao.OrderDao;
import com.czz.dao.OrderSettingDao;
import com.czz.exception.MyException;
import com.czz.health.pojo.Member;

import com.czz.health.pojo.Order;
import com.czz.health.pojo.OrderSetting;
import com.czz.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author czz
 * @create 2021-01-12 20:51
 **/
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderSettingDao orderSettingDao;


    /**
     * 提交预约订单
     *
     * @param orderInfo
     * @return
     */
    @Override
    @Transactional
    public Order submitOrder(Map<String, String> orderInfo) {
        //日期格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String orderDate = orderInfo.get("orderDate");
        Date date = null;
        try {
            date = dateFormat.parse(orderDate);
        } catch (ParseException e) {
            throw new MyException("所选日期不符合规范");
        }
        //判断体检日期是否符合要求
        OrderSetting orderSetting = orderSettingDao.findOrderDate(date);
        //没有当前日期的预约设置
        if (null == orderSetting) {
            throw new MyException("此时间不能预约,请选择其它日期");
        } else {
            //日期存在
            //根据预约设置判断是否超过预约人数上限；
            int reservations = orderSetting.getReservations();
            int number = orderSetting.getNumber();
            if (reservations >= number) {
                throw new MyException("当日预约人数已满，请选择其它日期");
            } else {
                Order order = new Order();
                //根据手机号判断用户是否存在
                String telephone = orderInfo.get("telephone");
                Member member = memberDao.findMemberByTelephone(telephone);
                //不存在即添加用户，获取用户id【这里id是为了后面查询是否重复预约】
                if (null == member) {
                    //member为空得new
                    member = new Member();
                    member.setPhoneNumber(telephone);
                    member.setRegTime(new Date());
                    member.setRemark("微信预约自动注册");
                    member.setName(orderInfo.get("name"));
                    member.setSex(orderInfo.get("sex"));
                    member.setIdCard(orderInfo.get("idCard"));
                    member.setPassword(telephone.substring(5)); // 默认密码
                    memberDao.add(member);
                    order.setMemberId(member.getId());
                    order.setSetmealId(Integer.valueOf(orderInfo.get("setmealId")));
                    order.setOrderDate(date);
                } else {
                    //存在即根据预约查询是否重复预约
                    order.setMemberId(member.getId());
                    order.setOrderDate(date);
                    order.setSetmealId(Integer.valueOf(orderInfo.get("setmealId")));
                    //根据预约订单查询是否已预约
                    List<Order> orderList = orderDao.findOrderByOrder(order);
                    if (!CollectionUtils.isEmpty(orderList)) {
                        throw new MyException("请勿重复预约");
                    }
                }
                //更新已预约人数
                orderSettingDao.updateReservationsByOrderSetting(orderSetting);
                order.setOrderType("微信预约");
                order.setOrderStatus(Order.ORDERSTATUS_NO);
                orderDao.add(order);
                return order;
            }
        }
    }

    @Override
    public Map<String, String> findById(int id) {
        Map<String, String> map = orderDao.findById(id);
        return map;
    }
}
