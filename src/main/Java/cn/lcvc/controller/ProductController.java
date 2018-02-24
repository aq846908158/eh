package cn.lcvc.controller;

import cn.lcvc.POJO.*;
import cn.lcvc.service.ProductService;
import cn.lcvc.service.ProductTypeService;
import cn.lcvc.service.SchoolService;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import static cn.lcvc.uitl.JWT.StringToTimestamp;



/**
 * @Author @wuruibao
 * @Date 2018/2/12 001216:45
 */
@Controller
@RequestMapping("/eh")
public class ProductController {

    @Autowired
    private  ProductTypeService productTypeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;

    @ResponseBody
    @RequestMapping(value = "/issuedProduct",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public  JsonResult addProduct(
            Product  product,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "imgUrlList") List imgUrlList,
            @RequestParam(value = "productTypeCode") Integer productTypeCode,
            @RequestParam(value = "schoolCode") Integer schoolCode,
            @RequestParam(value = "buyTimeCode")String buyTimeCode,
            @RequestParam(value = "expireCode")String expireCode
            ){
        JsonResult jsonResult=new JsonResult();
            //验证token信息  获取User
         User user;
        if (JWT.verifyJwt(token)) {
            TokenMessage tokenMessage=JWT.getPayloadDecoder(token);
             User user_Token=userService.getUser(tokenMessage.getUserId());
            String  jedisToken="";
            try {
                Jedis jedis = new Jedis("localhost");
                jedisToken= jedis.get(user_Token.getId()+"_token");
            }catch (Exception e){
                jsonResult.setErrorCode("501");
                jsonResult.setMessage("系统异常,请重新登录");
                return  jsonResult;
            }

            if (jedisToken.equals(token)){
              user=user_Token;
            }else {
                jsonResult.setErrorCode("501");
                jsonResult.setMessage("信息异常,请重新登录");
                return  jsonResult;
            }
        }
        else
        {
            jsonResult.setErrorCode("501");
            jsonResult.setMessage("信息异常,请重新登录");
            return  jsonResult;
        }
        // 验证学校
        School school =schoolService.isSchoolCode(schoolCode);
        if (school == null){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("宝贝区域信息错误,请刷新后重试");
            return jsonResult;
        }else {
            product.setSchool(school);
        }

        product.setBuyTime(StringToTimestamp(buyTimeCode));
        if (expireCode.length() == 0 || expireCode == null){
            product.setExpire(null);
        }else {
            product.setExpire(StringToTimestamp(expireCode));
        }

        NumberFormat nf = new DecimalFormat("#.00");
        product.setProductPrice(Double.valueOf(nf.format(product.getProductPrice())));

        ProductType  productType = productTypeService.isTypeCode(productTypeCode);
        if(productType == null){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("宝贝类别信息错误,请刷新后重试");
            return jsonResult;
        }
        product.setProductType(productType);


        jsonResult=productService.issueProduct(product,user,imgUrlList);

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
