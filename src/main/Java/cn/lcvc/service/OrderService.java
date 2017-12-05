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

        if(order != null){
            //对执行数据库操作的SQL语句 ，String类型要执行trim(),以免出现异常
            if(order.getOrderCode() != null && order.getOrderCode().trim().length() != 0) map.put("orderCode",order.getOrderCode().trim());


            if (order.getSellUser() != null){
                if(order.getSellUser().getUserName() != null && order.getSellUser().getUserName().trim().length() != 0){
                    User sellUserId=userDao.getUserBy_OneColumn("userName",order.getSellUser().getUserName());
                   if (sellUserId != null) map.put("sellUser",sellUserId);
                }
            }

          if (order.getBuyUser() != null){
               if(order.getBuyUser().getUserName() != null && order.getBuyUser().getUserName().trim().length() != 0){
                   User buyUserId=userDao.getUserBy_OneColumn("userName",order.getBuyUser().getUserName());
                   if (buyUserId != null) map.put("buyUser",buyUserId);
               }
           }



            if(order.getChalkUp() != null) map.put("chalkUp",order.getChalkUp());
        }


        if(lowOrderPrice != null && lowOrderPrice > 0.00) map.put("lowOrderPrice",lowOrderPrice);
        if(heightOrderPrice != null && heightOrderPrice > 0.00) map.put("heightOrderPrice",heightOrderPrice);

        List<Order> orders=orderDao.getOrder(Order.class,map);


        if (orders.size() > 0){
            map_order.put("order",orders);

            jsonResult.setErrorCode("200");
            jsonResult.setMessage("查询成功.");
            jsonResult.setItem(map_order);
        }else {
            jsonResult.setMessage("500");
            jsonResult.setMessage("无数据或出错.");
        }

        return  jsonResult;
    }


    /**
     *删除订单
     *@Author @wuruibao
     *@Date 2017/12/5 19:00
     *@params id:订单ID
     *@return  返回删除结果。
    */
    public  JsonResult deleteOrder(Integer id){
        JsonResult jsonResult = new JsonResult();


        if (id != null) {
            Order  order=orderDao.getOrder(id);
            if (order != null) {
                orderDao.deleteOrder(order);
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("订单不存在.");
                return  jsonResult;
            }
            if (orderDao.getOrder(id) == null){
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("删除成功.");
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("删除失败.");
            }
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("删除异常.");
        }







        return  jsonResult;
    }


















}
