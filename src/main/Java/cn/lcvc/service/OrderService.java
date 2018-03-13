package cn.lcvc.service;

import cn.lcvc.POJO.Order;
import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ShoppingCartItem;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.OrderDao;
import cn.lcvc.dao.ProductDao;
import cn.lcvc.dao.ShoppingCartItemDao;
import cn.lcvc.dao.UserDao;
import cn.lcvc.uitl.JsonResult;
import com.alibaba.fastjson.JSON;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


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
    private ShoppingCartItemDao shoppingCartItemDao;
    @Autowired
    private ShoppingCartItemService shoppingCartItemService;

    /**
     * 创建订单
     * @param buyUser 购买者
     * @return JsonResult信息
     */
    public JsonResult createOrder(User buyUser, List<Order> orders, List<ShoppingCartItem> shoppingCartItemList)
    {
        JsonResult jsonResult=new JsonResult();
        Order order=new Order();
        List<Order> orderList=new ArrayList<>();
        Product product=new  Product();
        for (int i=0;i<orders.size();i++){
            product=productDao.getProduct(orders.get(i).getProduct().getId());
            if(product == null)
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("商品不存在");
                return  jsonResult;
            }
            order.setProduct(product);
            order.setNumber(shoppingCartItemList.get(i).getNumber());
            order.setBuyUser(buyUser);
            order.setSellUser(product.getUser());
            order.setOrderState("未支付");
            order.setOrderPrice(shoppingCartItemList.get(i).getNumber()*product.getProductPrice());
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date=new Date();
            String orderCode=dateFormater.format(date)+product.getId()+buyUser.getId();
            order.setOrderCode(orderCode);
            order.setCreateTime(new Timestamp(System.currentTimeMillis()));
            order.setMessage(orders.get(i).getMessage());

            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setId(shoppingCartItemList.get(i).getId());
//            shoppingCartItem.setUser(buyUser);
//            shoppingCartItem.setProduct(product);
            shoppingCartItemDao.deleteShoppingCartItem(shoppingCartItem);//移除购物车项中加入订单的商品
            orderDao.addOrder(order);//创建订单

//            User buyUserOld=new User();
//            buyUser.setId(order.getBuyUser().getId());
//            buyUser.setUserName(order.getBuyUser().getUserName());
//            buyUser.setTrueName(order.getBuyUser().getTrueName());
//            order.setBuyUser(buyUserOld);
//
//            User sellUserOld=new User();
//            sellUserOld.setId(order.getSellUser().getId());
//            sellUserOld.setUserName(order.getSellUser().getUserName());
//            sellUserOld.setTrueName(order.getSellUser().getTrueName());
//            order.setSellUser(sellUserOld);
//
            Order orderPayList= new Order();
//            orderPayList.setOrderCode(order.getOrderCode());
//            orderPayList.setCreateTime(order.getCreateTime());
//            orderPayList.setOrderPrice(order.getOrderPrice());

            orderPayList.setId(order.getId());
            orderList.add(orderPayList);
            order=new Order();
            orderPayList=new Order();
            product=new  Product();
        }


//        Map<Object,Object> temp=new HashMap<>();
//        temp.put("order",order);
        jsonResult.setList(orderList);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("提交订单成功");
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
        Product product=order.getProduct();
        product.setProductNumber(product.getProductNumber()-order.getNumber());
        productDao.updateProduct(product);
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
                Product product=order.getProduct();
                product.setProductNumber(product.getProductNumber()-order.getNumber());
                productDao.updateProduct(product);
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
    public JsonResult getAllOrderManage(Order order, Double lowOrderPrice, Double heightOrderPrice,String column,String orderBy){
        JsonResult jsonResult = new JsonResult();
        Map<String, Object> map = new HashMap<String, Object>(); //查询所用Map容器
        Map<Object,Object> map_order= new HashMap<Object, Object>(); //存放查询所得数据

        if(order != null){
            //对执行数据库操作的SQL语句 ，String类型要执行trim(),以免出现异常
            if(order.getOrderCode() != null && order.getOrderCode().trim().length() != 0) map.put("orderCode",order.getOrderCode().trim());

            //添加根据商品查询条件
            if (order.getProduct() != null){
                if(order.getProduct().getProductName() != null && order.getProduct().getProductName().trim().length() != 0){
                    Product productId=productDao.getProductBy_OneColumn("productName",order.getProduct().getProductName());
                   if (productId != null) map.put("product",productId);

                }
            }
            //添加根据购买者查询条件
          if (order.getBuyUser() != null){
               if(order.getBuyUser().getUserName() != null && order.getBuyUser().getUserName().trim().length() != 0){
                   User buyUserId=userDao.getUserBy_OneColumn("userName",order.getBuyUser().getUserName());
                   map.put("buyUser",buyUserId);
               }
           }
            //添加根据订单状态查询条件
            if(order.getOrderState() != null&& order.getOrderState().trim().length()!=0) map.put("orderState",order.getOrderState());

            //添加根据订单号查询条件
            if (order.getOrderCode() != null && order.getOrderCode().trim().length()!=0){
                map.put("orderCode",order.getOrderCode());
            }
        }


        if(lowOrderPrice != null && lowOrderPrice > 0.00) map.put("lowOrderPrice",lowOrderPrice);
        if(heightOrderPrice != null && heightOrderPrice > 0.00) map.put("heightOrderPrice",heightOrderPrice);
        List<Order> orders=new ArrayList<>();

//        for (Map.Entry<String,Object> entry: map.entrySet()) {
//            System.out.println("key:"+entry.getKey());
//            System.out.println("value:"+entry.getValue());
//        }

            orders = orderDao.getOrder(Order.class, map,column,orderBy);

        if (orders.size() > 0){
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("查询成功");
            jsonResult.setList(orders);
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("无数据或出错");
        }

        return  jsonResult;
    }

    /**
     * 获取所有订单
     * @return 获取到的List存在 JsonResult.list中
     */
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
                return  jsonResult;
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("删除失败.");
                return  jsonResult;
            }
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("删除异常.");
            return  jsonResult;
        }

    }


    /**
     *获取个人订单
     *@Author @wuruibao
     *@Date 2017/12/5 19:00
     *@params id:订单ID
     *@return
     */
    public  JsonResult getOrderByUser(Integer uid,String productName){
        JsonResult jsonResult = new JsonResult();

        if(uid==null||uid==0)
        {
            jsonResult.setMessage("查询失败");
            jsonResult.setErrorCode("500");
        }
        Map<String,Object> map=new HashMap<>();
        if(productName!=null&&productName!="")
        {
            map.put("product",productName);
        }
        User user=userDao.getUser(uid);
        List<Order> orders=orderDao.getOrderByUser(user,map);
        jsonResult.setList(orders);
        jsonResult.setMessage("查询成功");
        jsonResult.setErrorCode("200");
        return jsonResult;
    }


    /**
     * 获取用户个人未支付订单信息，
     * @param user
     * @return
     */
    public  JsonResult getOderisPayList(User user){
        JsonResult jsonResult=new JsonResult();

        Map<String,Object> sqlMap=new HashMap<>();
        sqlMap.put("orderState","未支付");

        List<Order> orderList=orderDao.getOrderByUser(user,sqlMap);
        Order orderPay= new Order();
        Product p=new Product();
        User u=new User();
        List<Order> orderPayList=new ArrayList<Order>();
        Double price=0.00D;
        for (int i=0;i<orderList.size();i++){
            orderPay.setId(orderList.get(i).getId());
            p.setId(orderList.get(i).getProduct().getId());
            p.setProductName(orderList.get(i).getProduct().getProductName());
            orderPay.setProduct(p);
            orderPay.setOrderCode(orderList.get(i).getOrderCode());
            u.setUserName(orderList.get(i).getSellUser().getUserName());
            orderPay.setSellUser(u);
            orderPay.setOrderPrice(orderList.get(i).getOrderPrice());
            price+=orderPay.getOrderPrice();
            orderPayList.add(orderPay);
            orderPay=new Order();
            p=new Product();
        }


        if (orderPayList.size() >0){
            Map<Object,Object> map=new HashMap<>();
            map.put("orderTime",orderList.get(orderList.size()-1).getCreateTime());
            map.put("totalPrice",price);
            jsonResult.setItem(map);
            jsonResult.setList(orderPayList);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("ok");
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("error");
        }

        return  jsonResult;
    }











}
