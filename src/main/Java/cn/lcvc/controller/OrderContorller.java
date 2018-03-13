package cn.lcvc.controller;

import cn.lcvc.POJO.*;
import cn.lcvc.service.*;
import cn.lcvc.uitl.JWT;
import cn.lcvc.uitl.JsonResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderContorller {
    @Autowired
    OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductImgService productImgService;
    @Autowired
    private ShoppingCartItemService shoppingCartItemService;
    @Autowired
    private ProductService productSerice;

    @ResponseBody
    @RequestMapping(value = "/order",method =  RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult slectOrder(Order order,@RequestParam(value = "token") String token,@RequestParam(value = "productName") String productName)
    {
        JsonResult jsonResult=null;
        User user;
        if (token != null && token.trim().length() > 0){
            if (JWT.verifyJwt(token)) {
                TokenMessage tokenMessage=JWT.getPayloadDecoder(token);
                User user_Token=userService.getUser(tokenMessage.getUserId());
                String  jedisToken="";
                try {
                    Jedis jedis = new Jedis("localhost");
                    jedisToken= jedis.get(user_Token.getId()+"_token");
                }catch (Exception e){
                    jsonResult.setErrorCode("501");
                    jsonResult.setMessage("系统异常,请重新登录");
                    return  jsonResult;
                }

                if (jedisToken.equals(token)){
                    user=user_Token;
                }else {
                    jsonResult.setErrorCode("501");
                    jsonResult.setMessage("信息异常,请重新登录");
                    return  jsonResult;
                }
            }
            else
            {
                jsonResult.setErrorCode("501");
                jsonResult.setMessage("信息异常,请重新登录");
                return  jsonResult;
            }
        }else {
            user=null;
        }

        if(user!=null)
        {
            jsonResult=orderService.getOrderByUser(user.getId(),productName);

        }
        return jsonResult;
    }


    @ResponseBody
    @RequestMapping(value = "/order",method =  RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult addOrder(@RequestParam(value = "token") String token,@RequestParam(value = "ds") String orderData){
        JsonResult jsonResult=null;
        User user;
        if (token != null && token.trim().length() > 0){
            if (JWT.verifyJwt(token)) {
                TokenMessage tokenMessage=JWT.getPayloadDecoder(token);
                User user_Token=userService.getUser(tokenMessage.getUserId());
                String  jedisToken="";
                try {
                    Jedis jedis = new Jedis("localhost");
                    jedisToken= jedis.get(user_Token.getId()+"_token");
                }catch (Exception e){
                    jsonResult.setErrorCode("501");
                    jsonResult.setMessage("系统异常,请重新登录");
                    return  jsonResult;
                }

                if (jedisToken.equals(token)){
                    user=user_Token;
                     List<Order> orderList = new ArrayList<Order>();
                    JSONArray json=JSONArray.fromObject(orderData);//解析字符串
                    JSONObject jsonOne;
                    //遍历字符串
                    for(int i=0;i<json.size();i++){
                        Order order=new Order();
                        Product p=new Product();
                        jsonOne = json.getJSONObject(i);
                        p.setId((Integer) jsonOne.get("pid"));
                        order.setProduct(p);
                        order.setMessage((String) jsonOne.get("message"));
                        orderList.add(order);
                    }
                    JsonResult j=new JsonResult();
                    j=shoppingCartItemService.getShoppingCartItemList(user);
                    List<ShoppingCartItem> cartList =j.getList();
                    List<ShoppingCartItem> cartOrder=new ArrayList<ShoppingCartItem>();
                    ShoppingCartItem shoppingCart=new ShoppingCartItem();
                    //遍历结算的商品，从购物车中取出该商品的对象
                    for(int i=0;i<orderList.size();i++){
                        for(int c=0;c<cartList.size();c++){
                            if (orderList.get(i).getProduct().equals(cartList.get(c).getProduct())){
                                shoppingCart=cartList.get(c);
                                cartOrder.add(shoppingCart);
                            }
                        }
                    }

                    jsonResult=orderService.createOrder(user,orderList,cartOrder);


                }else {
                    jsonResult.setErrorCode("501");
                    jsonResult.setMessage("信息异常,请重新登录");
                    return  jsonResult;
                }
            }
            else
            {
                jsonResult.setErrorCode("501");
                jsonResult.setMessage("信息异常,请重新登录");
                return  jsonResult;
            }
        }else {
            jsonResult.setErrorCode("501");
            jsonResult.setMessage("信息异常,请重新登录");
            return  jsonResult;
        }

        return jsonResult;
    }


    @ResponseBody
    @RequestMapping(value = "/doOrder",method =  RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult submitOrder(@RequestParam(value = "token") String token){
        JsonResult jsonResult =new JsonResult();

        User user;
        if (token != null && token.trim().length() > 0){
            if (JWT.verifyJwt(token)) {
                TokenMessage tokenMessage=JWT.getPayloadDecoder(token);
                User user_Token=userService.getUser(tokenMessage.getUserId());
                String  jedisToken="";
                try {
                    Jedis jedis = new Jedis("localhost");
                    jedisToken= jedis.get(user_Token.getId()+"_token");
                }catch (Exception e){
                    jsonResult.setErrorCode("501");
                    jsonResult.setMessage("系统异常,请重新登录");
                    return  jsonResult;
                }

                if (jedisToken.equals(token)){
                    user=user_Token;
                    jsonResult=shoppingCartItemService.getShoppingCartItemList(user);
                    return  jsonResult;
                }else {
                    jsonResult.setErrorCode("501");
                    jsonResult.setMessage("信息异常,请重新登录");
                    return  jsonResult;
                }
            }
            else
            {
                jsonResult.setErrorCode("501");
                jsonResult.setMessage("信息异常,请重新登录");
                return  jsonResult;
            }
        }else {
            jsonResult.setErrorCode("501");
            jsonResult.setMessage("信息异常,请重新登录");
            return  jsonResult;
        }


    }


    @ResponseBody
    @RequestMapping(value = "/payOrder",method =  RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult payOrder(@RequestParam(value = "token") String token){
        JsonResult jsonResult =new JsonResult();

        User user;
        if (token != null && token.trim().length() > 0){
            if (JWT.verifyJwt(token)) {
                TokenMessage tokenMessage=JWT.getPayloadDecoder(token);
                User user_Token=userService.getUser(tokenMessage.getUserId());
                String  jedisToken="";
                try {
                    Jedis jedis = new Jedis("localhost");
                    jedisToken= jedis.get(user_Token.getId()+"_token");
                }catch (Exception e){
                    jsonResult.setErrorCode("501");
                    jsonResult.setMessage("系统异常,请重新登录");
                    return  jsonResult;
                }

                if (jedisToken.equals(token)){
                    user=user_Token;
                    jsonResult=orderService.getOderisPayList(user);
                    return  jsonResult;
                }else {
                    jsonResult.setErrorCode("501");
                    jsonResult.setMessage("信息异常,请重新登录");
                    return  jsonResult;
                }
            }
            else
            {
                jsonResult.setErrorCode("501");
                jsonResult.setMessage("信息异常,请重新登录");
                return  jsonResult;
            }
        }else {
            jsonResult.setErrorCode("501");
            jsonResult.setMessage("信息异常,请重新登录");
            return  jsonResult;
        }


    }

}
