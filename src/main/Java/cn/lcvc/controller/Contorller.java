package cn.lcvc.controller;

import cn.lcvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tt")
public class Contorller {
    @Autowired
    private UserService userService;

    @RequestMapping("/getAllUser")
    public String getAllUser(HttpServletRequest request) {
        System.out.println("111111--------------------------");
        return "/index";
    }

    @RequestMapping("/getAllUser1")
    public String getAllUser1(HttpServletRequest request) {
        System.out.println("22222--------------------------");
        return "/index";
    }
}
