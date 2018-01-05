package cn.lcvc.controller;


import cn.lcvc.POJO.User;
import cn.lcvc.service.UserService;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserContorller {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/user")
    public JsonResult getUser(){
        JsonResult jsonResult =new JsonResult();

        User user=new User();
        jsonResult=userService.getAllUserManage(user,0,0,0,0);

        return  jsonResult;

    }





}