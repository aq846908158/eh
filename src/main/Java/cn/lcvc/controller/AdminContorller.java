package cn.lcvc.controller;

import cn.lcvc.dao.AdminDao;
import cn.lcvc.service.AdminService;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Controller
@RequestMapping(value = "/admin")
public class AdminContorller {

    @Autowired
    private AdminService adminService;
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public JsonResult login(HttpServletRequest request) {

        JsonResult jsonResult=adminService.login("user1111","user1111");
        return jsonResult;
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public JsonResult login1(HttpServletRequest request) {

        JsonResult jsonResult=adminService.login("user1111","user1111");
        return jsonResult;
    }

    @RequestMapping(value = "/login",method = RequestMethod.DELETE)
    public JsonResult login2(HttpServletRequest request) {

        JsonResult jsonResult=adminService.login("user1111","user1111");
        return jsonResult;
    }
}
