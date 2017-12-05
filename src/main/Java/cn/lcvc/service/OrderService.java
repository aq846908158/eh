package cn.lcvc.service;

import cn.lcvc.POJO.Order;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.OrderDao;
import cn.lcvc.dao.UserDao;
import cn.lcvc.uitl.JsonResult;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 *@Author @wuruibao
 *@Date 2017-12-5 10:55:59
*/
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;



    /**
     * 全站点订单管理,
     *@Author @wuruibao
     *@Date 2017-12-5 11:06:40
     *@params    order:产品对象. lowOrderPrice:订单最低价 , heightOrderPrice:订单最高价
     *@return 根据条件查询结果 将list集合放入jsonResult的Map集合里，返回到前台
     */
    public JsonResult getAllOrderManage(Order order, Double lowOrderPrice, Double heightOrderPrice){
        JsonResult jsonResult = new JsonResult();
        Map<String, Object> map = new HashMap<String, Object>(); //查询所用Map容器
        Map<Object,Object> map_order= new HashMap<Object, Object>(); //存放查询所得数据

        //对执行数据库操作的SQL语句 ，String类型要执行trim(),以免出现异常
        if(order.getOrderCode() != null && order.getOrderCode().trim().length() != 0) map.put("orderCode",order.getOrderCode().trim());
        if(order.getSellUser().getUserName() != null && order.getSellUser().getUserName().trim().length() != 0){
            User sellUserId=userDao.getUserBy_OneColumn("userName",order.getSellUser().getUserName());
            if (sellUserId != null) map.put("sellUser",sellUserId.getId());
        }

        if(order.getBuyUser().getUserName() != null && order.getBuyUser().getUserName().trim().length() != 0){
            User buyUserId=userDao.getUserBy_OneColumn("userName",order.getBuyUser().getUserName());
            if (buyUserId != null) map.put("buyUser",buyUserId.getId());
        }

        if(order.getChalkUp() != null) map.put("chalkUp",order.getChalkUp());
        if(lowOrderPrice != null && lowOrderPrice > 0.00) map.put("lowOrderPrice",lowOrderPrice);
        if(heightOrderPrice != null && heightOrderPrice > 0.00) map.put("heightOrderPrice",heightOrderPrice);

        List<Order> orders=orderDao.getOrder(Order.class,map);









        return  jsonResult;
    }





















}
