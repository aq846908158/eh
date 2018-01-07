package cn.lcvc.controller;


import cn.lcvc.POJO.User;
import cn.lcvc.service.UserService;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(value = "/user")
public class UserContorller {

    @Autowired
    private UserService userService;

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





}