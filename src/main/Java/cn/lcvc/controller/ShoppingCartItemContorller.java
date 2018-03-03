package cn.lcvc.controller;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ShoppingCartItem;
import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.POJO.User;
import cn.lcvc.service.ProductService;
import cn.lcvc.service.ShoppingCartItemService;
import cn.lcvc.service.UserService;
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
import java.util.List;

/**
 * @Author @wuruibao
 * @Date 2018/3/1 000120:31
 */
@Controller
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartItemContorller {

    @Autowired
    private ShoppingCartItemService shoppingCart;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping(value = "/addShoppingCart",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public JsonResult addShoppingCartItem(
            @RequestParam(value = "pid") Integer pid,
            @RequestParam(value = "pNumber") Integer pNumber,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "ds") String cookieDa){
        JsonResult jsonResult=new JsonResult();

        //验证token
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


        //已登录
        if (user != null){
            jsonResult=shoppingCart.addShoppingCartItem(pid,pNumber,user);
        }else {
          // 未登录
            JSONArray json=JSONArray.fromObject(cookieDa);
            JSONObject jsonOne;
            List<ShoppingCartItem> cartList = new ArrayList<ShoppingCartItem>();
            for(int i=0;i<json.size();i++){
                ShoppingCartItem cartItem=new ShoppingCartItem();
                Product p=new Product();
                jsonOne = json.getJSONObject(i);
                p.setId((Integer) jsonOne.get("pid"));
                cartItem.setProduct(p);
                cartItem.setNumber((Integer) jsonOne.get("pNumber"));
                cartList.add(cartItem);
            }
            jsonResult=shoppingCart.addShoppingCartItemBy_Session(cartList,pid,pNumber);

        }


        return  jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/addCookieShoppingCart",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public JsonResult addCookieShoppingCart(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "ds") String cookieDa)
    {
        JsonResult jsonResult = new JsonResult();

        //验证token
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

                    JSONArray json=JSONArray.fromObject(cookieDa);
                    JSONObject jsonOne;
                    List<ShoppingCartItem> cartList = new ArrayList<ShoppingCartItem>();
                    for(int i=0;i<json.size();i++){
                        ShoppingCartItem cartItem=new ShoppingCartItem();
                        Product p=new Product();
                        jsonOne = json.getJSONObject(i);
                        p.setId((Integer) jsonOne.get("pid"));
                        cartItem.setProduct(p);
                        cartItem.setNumber((Integer) jsonOne.get("pNumber"));
                        cartList.add(cartItem);
                    }

                    jsonResult=shoppingCart.sessionShoppingCartToShoppingCart(cartList,user);
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
        }

        return  jsonResult;
    }


}
