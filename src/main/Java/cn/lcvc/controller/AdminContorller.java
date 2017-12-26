package cn.lcvc.controller;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.POJO.User;
import cn.lcvc.service.AdminService;
import cn.lcvc.service.UserService;
import cn.lcvc.uitl.JWT;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminContorller {
    @Autowired
    private AdminService adminService;
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public JsonResult login(@RequestParam(value = "userName") String userNmae,@RequestParam(value = "userPassword") String userPassword) {
        JsonResult jsonResult=adminService.login(userNmae,userPassword);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/getLoginMessage",method = RequestMethod.GET)
    public JsonResult getLoginMessage(@RequestParam(value = "token") String token) {
        JsonResult jsonResult=new JsonResult();
        if (JWT.verifyJwt(token))
        {
            TokenMessage tokenMessage=JWT.getPayloadDecoder(token);
            Admin admin=adminService.getAdmin(tokenMessage.getAdminId());
            jsonResult.getItem().put("adminName",admin.getTrueName());
            jsonResult.getItem().put("title",admin.getTitle());
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("获取成功");
            return  jsonResult;
        }
        else
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("获取失败");
            return  jsonResult;
        }

    }

}

