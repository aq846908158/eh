package cn.lcvc.controller;

import cn.lcvc.POJO.Order;
import cn.lcvc.service.OrderService;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class OrderContorller {
    @Autowired
    OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "/order",method =  RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult slectOrder(Order order)
    {

        JsonResult jsonResult=orderService.getAllOrderManage(order,null,null);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/order",method =  RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    public JsonResult deleteOrder(Order order)
    {
        JsonResult jsonResult=orderService.deleteOrder(order.getId());
        return jsonResult;
    }

}
