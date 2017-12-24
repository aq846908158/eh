package cn.lcvc.controller;

import cn.lcvc.service.UserService;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class Contorller1 {
    @Autowired
    private UserService userService;
    @ResponseBody
    @RequestMapping(value = "/login")
    public JsonResult getAllUser(@RequestParam(value = "name") Integer name) {
        System.out.println(name);
        JsonResult jsonResult=userService.login("user1111","12345678");
        return jsonResult;
    }

}

