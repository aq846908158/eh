package cn.lcvc.controller;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.AdminPermissions;
import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.service.AdminPermissionsService;
import cn.lcvc.service.AdminService;
import cn.lcvc.uitl.JWT;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author @wuruibao
 * @Date 2018/1/123:23
 */
@Controller
@RequestMapping(value = "/adminPermissions")
public class AdminPermissionsContorller {

    @Autowired
    private AdminPermissionsService adminPermissionsService;
    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping(value = "/adminPermissions",method = RequestMethod.GET)
    public JsonResult getAdminPermissions(Admin admin, @RequestParam(value = "adminPermissons") String permissons) throws UnsupportedEncodingException {
        byte[] b=admin.getTrueName().getBytes("ISO-8859-1");//用tomcat的格式（iso-8859-1）方式去读。
        String trueName=new String(b,"utf-8");//采用utf-8去接string
        admin.setTrueName(trueName);
        AdminPermissions adminPermissions=new AdminPermissions();

        if (permissons.equals("low"))adminPermissions.setLow(true);
        if (permissons.equals("middle")) adminPermissions.setMiddle(true);
        if (permissons.equals("height")) adminPermissions.setHeight(true);
        JsonResult jsonResult = adminPermissionsService.getAdminPermissions(adminPermissions,admin);

        return  jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/addAdminPermissions",method = RequestMethod.POST)
    public  JsonResult addAdminPermissions(Admin admin,@RequestParam(value = "token") String token){
        JsonResult jsonResult=new JsonResult();
        Map<Object,Object> map=new HashMap<Object, Object>();

        //获取admin登录信息
        if (JWT.verifyJwt(token)) {
            TokenMessage tokenMessage=JWT.getPayloadDecoder(token);
            Admin adminlogin=adminService.getAdmin(tokenMessage.getAdminId());
            String  jedisToken="";
            try {
                Jedis jedis = new Jedis("localhost");
                jedisToken= jedis.get(adminlogin.getId()+"_token");
            }catch (Exception e){
                jsonResult.setErrorCode("501");
                jsonResult.setMessage("服务端:获取失败,请重新登录.");
                return  jsonResult;
            }

            if (jedisToken.equals(token)){
              jsonResult = adminPermissionsService.registerAdminPermissions(admin,adminlogin,map);
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端:信息异常,请重新登录.");
                return  jsonResult;
            }


        }

        return  jsonResult;
    }

    @ResponseBody
    @RequestMapping(value =  "/getPermissions",method = RequestMethod.GET)
    public JsonResult getPermissions(@RequestParam(value = "token") String token){
        JsonResult jsonResult=new JsonResult();

        Boolean permissions=adminPermissionsService.getPermissions(token);

        if (permissions){
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("服务端：SUCCESS.");
        }else {
            jsonResult.setErrorCode("201");
            jsonResult.setMessage("服务端：对不起，您无权操作.");
        }

        return  jsonResult;
    }
}
