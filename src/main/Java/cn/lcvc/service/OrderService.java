package cn.lcvc.service;

import cn.lcvc.POJO.Order;
import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.OrderDao;
import cn.lcvc.dao.ProductDao;
import cn.lcvc.dao.UserDao;
import cn.lcvc.uitl.JsonResult;
import com.alibaba.fastjson.JSON;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ShoppingCartItemService shoppingCartItemService;
    /**
     * 创建订单
     * @param productId 商品Id
     * @param number 购买数量
     * @param buyUserId session中登录信息
     * @param orderMessage 订单备注
     * @return JsonResult信息
     */
    public JsonResult createOrder(Integer productId,Integer number,Integer buyUserId,String orderMessage)
    {
        JsonResult jsonResult=new JsonResult();
        Order order=new Order();
        Product product=productDao.getProduct(productId);
        User user=userDao.getUser(buyUserId);

        if(product==null)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("商品不存在");
            return  jsonResult;
        }
        if(user==null)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("用户不存在");
            return  jsonResult;
        }

        product.setId(productId);
        order.setProduct(product);
        order.setNumber(number);
        order.setBuyUser(user);
        order.setOrderState("0");
        order.setOrderPrice(number*product.getProductPrice());
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date=new Date();
        String orderCode=dateFormater.format(date)+product.getId()+user.getId();
        order.setOrderCode(orderCode);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setMessage(orderMessage);
        orderDao.addOrder(order);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("创建成功");
        return jsonResult;
    }

    /**
     * 订单支付
     * @param orderId 订单Id
     * @return JsonResult信息
     */
    public JsonResult orderPay(Integer orderId)
    {
        JsonResult jsonResult=new JsonResult();
        Order order=orderDao.getOrder(orderId);
        if(order==null){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("订单不存在");
            return  jsonResult;
        }
        if(!shoppingCartItemService.productNumberIs(order.getProduct().getId(),order.getNumber()))
        {
            jsonResult.setMessage("商品库存不足");
            jsonResult.setErrorCode("500");
            return jsonResult;
        }

        if(!order.getOrderState().equals("0"))
        {
            jsonResult.setMessage("商品【"+order.getProduct().getProductName()+"】已经支付,不可以再次支付");
            jsonResult.setErrorCode("500");
            return jsonResult;
        }
        order.setOrderState("1");
        orderDao.updateOrder(order);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("付款成功");
        return jsonResult;
    }

    /**
     * 订单合并支付
     * @param orders 订单List集合
     * @return JsonResult信息
     */
    public JsonResult orderMargePay(List<Order> orders)
    {
        JsonResult jsonResult=new JsonResult();

        for (int i = 0; i < orders.size(); i++) {
            Order order =  orders.get(i);
            if(!shoppingCartItemService.productNumberIs(order.getProduct().getId(),order.getNumber()))
            {
                jsonResult.setMessage("商品【"+order.getProduct().getProductName()+"】库存不足,请修改购买数量或者删除订单再支付");
                jsonResult.setErrorCode("500");
                return jsonResult;
            }
            if(!order.getOrderState().equals("0"))
            {
                jsonResult.setMessage("商品【"+order.getProduct().getProductName()+"】已经支付,不可以再次支付");
                jsonResult.setErrorCode("500");
                return jsonResult;
            }
        }

        for (int i = 0; i < orders.size(); i++) {
            Order order =  orders.get(i);
            order.setOrderState("1");
            if(order!=null&&order.getId()!=0) {
                orderDao.updateOrder(order);
            }
        }
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("支付成功");
        return  jsonResult;
    }
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


            if (order.getProduct() != null){
                if(order.getProduct().getProductName() != null && order.getProduct().getProductName().trim().length() != 0){
                    Product productId=productDao.getProductBy_OneColumn("productName",order.getProduct().getProductName());
                   if (productId != null) map.put("product",productId);
                }
            }

          if (order.getBuyUser() != null){
               if(order.getBuyUser().getUserName() != null && order.getBuyUser().getUserName().trim().length() != 0){
                   User buyUserId=userDao.getUserBy_OneColumn("userName",order.getBuyUser().getUserName());
                   if (buyUserId != null) map.put("buyUser",buyUserId);
               }
           }



            if(order.getOrderState() != null) map.put("orderState",order.getOrderState());
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

    public  JsonResult getAllOrderList()
    {
        JsonResult jsonResult=new JsonResult();
        jsonResult.setList(orderDao.getOrderList());
        return jsonResult;
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
