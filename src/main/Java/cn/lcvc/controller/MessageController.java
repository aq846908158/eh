package cn.lcvc.controller;

import cn.lcvc.POJO.Message;
import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.POJO.User;
import cn.lcvc.service.MessageService;
import cn.lcvc.service.ProductService;
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
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping(value = "/message",method =  RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult slectOrder(@RequestParam(value = "token") String token, @RequestParam(value = "pid") Integer pid,@RequestParam(value = "text") String text)
    {
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
                    jsonResult=messageService.registerMessage(user,pid,text,null);
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