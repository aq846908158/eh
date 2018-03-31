package cn.lcvc.controller;

import cn.lcvc.POJO.Favorites;
import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.POJO.User;
import cn.lcvc.service.FavoritesService;
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

@Controller
@RequestMapping(value = "favorites")
public class FavoritesContorller {

    @Autowired
    private FavoritesService favoritesService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult addFavorites(@RequestParam(value = "token") String token,@RequestParam(value = "pid") Integer pid){
        JsonResult jsonResult=new JsonResult();

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
                    Favorites fa=new Favorites();
                    Product p=new Product();
                    p.setId(pid);
                    fa.setUser(user);
                    fa.setProduct(p);
                    jsonResult=favoritesService.registerOrCancelFavorites(fa);
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
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("天呐,您还没登录呢");
        }

        return  jsonResult;


    }

    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult getFavorites(@RequestParam(value = "token") String token){
        JsonResult jsonResult=new JsonResult();

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
                    jsonResult=favoritesService.getFavorites(user);
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
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("天呐,您还没登录呢");
        }

        return  jsonResult;

    }


    @ResponseBody
    @RequestMapping(value = "/del",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult delFavorites(@RequestParam(value = "token") String token,@RequestParam(value = "pid") Integer pid){
            JsonResult jsonResult = new JsonResult();

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
                    jsonResult=favoritesService.deleteFavorites(user,pid);
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
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("天呐,您还没登录呢");
        }

        return  jsonResult;
    }

}