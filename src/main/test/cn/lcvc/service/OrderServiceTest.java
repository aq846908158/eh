package cn.lcvc.service;

import cn.lcvc.POJO.Order;
import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.UserDao;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author @wuruibao
 * @Date 2017/12/513:06
 */
public class OrderServiceTest extends BaseJunit {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserDao userDao;

    /*全站点订单 管理  100%*/
    @Test
    public void getOrderTest() {
        Order order = new Order();
        User user = new User();
        User user_2 = new User();
        order.setOrderCode("2017");

        user.setUserName("user");
//        user_2.setUserName("user1111");
//        order.setSellUser(user);
        order.setBuyUser(user);
        //  order.setChalkUp(true);
        JsonResult jsonResult = orderService.getAllOrderManage(order, -92.07, -450.01,"","");
        List<Order> orders = (List<Order>) jsonResult.getItem().get("order");

        for (int i = 0; i < orders.size(); i++) {
            System.out.println("订单号 " + orders.get(i).getOrderCode());
            System.out.println("商品 " + orders.get(i).getProduct().getProductName());
            System.out.println("购买人 " + orders.get(i).getBuyUser().getTrueName());
            System.out.println("订单状态 " + orders.get(i).getOrderState());
        }

    }

    /*订单删除 100%*/
    @Test
    public void deleteOrderTest() {
    JsonResult jsonResult=orderService.deleteOrder(4);

    System.out.println("删除结果: "+jsonResult.getMessage());

    }

    @Test
    public void createOrder()
    {
//        JsonResult jsonResult=orderService.createOrder(2,2,8,"明天中午");
//        System.out.println(jsonResult.getMessage());
    }
    @Test
    public void orderPay()
    {
        JsonResult jsonResult=orderService.orderPay(1);
        System.out.println(jsonResult.getMessage());
    }

    @Test
    public void orderPayMerge()
    {

        JsonResult jsonResult=orderService.getAllOrderList();
        List<Order> orders=jsonResult.getList();
        JsonResult jsonResult1=orderService.orderMargePay(orders);
        System.out.println(jsonResult1.getMessage());
    }



}