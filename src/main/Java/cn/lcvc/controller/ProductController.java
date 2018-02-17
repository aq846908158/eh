package cn.lcvc.controller;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.POJO.User;
import cn.lcvc.service.ProductService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static cn.lcvc.uitl.JWT.checkoutUserToken;
import static cn.lcvc.uitl.JWT.testStringToTimestamp;

/**
 * @Author @wuruibao
 * @Date 2018/2/12 001216:45
 */
@Controller
@RequestMapping("/eh")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping(value = "/issuedProduct",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public  JsonResult addProduct(
            Product  product,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "imgUrlList") List imgUrlList,
            @RequestParam(value = "productTypeCode") Integer productType,
            @RequestParam(value = "schoolCode") Integer schoolCode,
            @RequestParam(value = "buyTimeCode")String buyTimeCode,
            @RequestParam(value = "expireCode")String expireCode
            ){
        JsonResult jsonResult=new JsonResult();
            //验证token信息  获取User
         User user=checkoutUserToken(token);
        if (user == null){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请重新登录");
            return  jsonResult;
        }
        product.setBuyTime(testStringToTimestamp(buyTimeCode));
        
        return  jsonResult;



    }

    @ResponseBody
    @RequestMapping(value = "/producImagePush",method = RequestMethod.POST)
    public JsonResult getProducImagePush(@RequestParam(value = "imgUrlList")  List imgUrlList){
        JsonResult jsonResult=new JsonResult();
        if (imgUrlList != null){
            for (int i=0;i<imgUrlList.size();i++){
                System.out.println(imgUrlList.get(i));
            }
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("成功");
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("no成功");
        }

        return  jsonResult;
    }

}
