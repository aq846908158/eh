package cn.lcvc.controller;


import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.POJO.User;
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

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(value = "/user")
public class UserContorller {

    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult register(User user,@RequestParam(value = "confirmPassword") String confirmPassword){
        JsonResult jsonResult =userService.registerUser(user,confirmPassword);
        return  jsonResult;
    }
    @ResponseBody
    @RequestMapping(value = "/repeatName",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult repeatName(@RequestParam(value = "userName") String userName){
        JsonResult jsonResult =userService.repeatName(userName);
        return  jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public  JsonResult login(@RequestParam(value = "userName") String userName,@RequestParam(value = "userPassword") String userPassword){
        JsonResult jsonResult = userService.login(userName,userPassword);
        return  jsonResult;

    }

    @ResponseBody
    @RequestMapping(value = "/getLoginMessage",method = RequestMethod.GET)
    public JsonResult getLoginMessage(@RequestParam(value = "token") String token) {

        JsonResult jsonResult=new JsonResult();

        if (JWT.verifyJwt(token))
        {
            TokenMessage tokenMessage=JWT.getPayloadDecoder(token);
            User user=userService.getUser(tokenMessage.getUserId());
            String  jedisToken="";
            try {
                Jedis jedis = new Jedis("localhost");
                jedisToken= jedis.get(user.getId()+"_token");
            }catch (Exception e){
                jsonResult.setErrorCode("501");
                jsonResult.setMessage("服务端：系统异常,请重新登录");
                return  jsonResult;
            }

            if (jedisToken.equals(token)){
                jsonResult.getItem().put("userName",user.getUserName());
                jsonResult.getItem().put("trueName",user.getTrueName());
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("服务端：获取成功");
                return  jsonResult;
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端：信息异常,请重新登录");
                return  jsonResult;
            }


        }
        else
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：信息异常,请重新登录");
            return  jsonResult;
        }

    }



    @ResponseBody
    @RequestMapping(value = "/user",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult getUser(User user,
                              @RequestParam(value = "lowSellNumber") Integer lowSellNumber,
                              @RequestParam(value = "hiSellNumber") Integer hiSellNumber,
                              @RequestParam(value = "lowForSaleNumber") Integer lowForSaleNumber,
                              @RequestParam(value = "hiForSaleNumber") Integer hiForSaleNumber)throws UnsupportedEncodingException {
        JsonResult jsonResult =new JsonResult();

        if (user.getTrueName() != null && user.getTrueName().trim().length() != 0){
            byte[] b=user.getTrueName().getBytes("ISO-8859-1");//用tomcat的格式（iso-8859-1）方式去读。
            String trueName=new String(b,"utf-8");//采用utf-8去接string
            user.setTrueName(trueName);
        }

        jsonResult=userService.getAllUserManage(user,lowSellNumber,hiSellNumber,lowForSaleNumber,hiForSaleNumber);

        return  jsonResult;

    }


    @ResponseBody
    @RequestMapping(value = "/updateUserBanLogin",method = RequestMethod.GET)
    public  JsonResult updateUserBanLogin(User user,@RequestParam(value = "banLoginCode") Integer banLoginCode){

        JsonResult jsonResult =userService.updateBanLogin(user.getId(),banLoginCode);
        return  jsonResult;

    }

    @ResponseBody
    @RequestMapping(value = "/updateUserBanSell",method = RequestMethod.GET)
    public  JsonResult updateUserBanSell(User user,@RequestParam(value = "banSellCode") Integer banSellCode){

        JsonResult jsonResult =userService.updateBanSell(user.getId(),banSellCode);
        return  jsonResult;

    }


}