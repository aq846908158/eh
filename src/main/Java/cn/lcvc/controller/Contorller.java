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
        request.setAttribute("userList", userService.getAllUser());
        return "/index.jsp";
    }
}
