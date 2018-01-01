package cn.lcvc.controller;

import cn.lcvc.POJO.Order;
import cn.lcvc.POJO.ProductType;
import cn.lcvc.service.ProductTypeService;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class ProductTypeController {

    @Autowired
    ProductTypeService productTypeService;

    @ResponseBody
    @RequestMapping(value = "/productType",method =  RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult getProductTypeMessage(ProductType productType)
    {
        JsonResult jsonResult = productTypeService.getProductType(productType);
        return jsonResult;
    }
}