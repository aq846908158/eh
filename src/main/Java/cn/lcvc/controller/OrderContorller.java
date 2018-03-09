package cn.lcvc.controller;

import cn.lcvc.POJO.*;
import cn.lcvc.service.OrderService;
import cn.lcvc.service.ProductImgService;
import cn.lcvc.service.ShoppingCartItemService;
import cn.lcvc.service.UserService;
import cn.lcvc.uitl.JWT;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

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
    public JsonResult addOrder(@RequestParam(value = "token") String token){
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

}
