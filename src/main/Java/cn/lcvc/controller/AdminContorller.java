package cn.lcvc.controller;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.TokenMessage;
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

@Controller
@RequestMapping("/admin")
public class AdminContorller {
    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public JsonResult login(@RequestParam(value = "userName") String userNmae, @RequestParam(value = "userPassword") String userPassword) {
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
            String  jedisToken="";
            try {
                Jedis jedis = new Jedis("localhost");
                jedisToken= jedis.get(admin.getId()+"_token");
            }catch (Exception e){
                jsonResult.setErrorCode("501");
                jsonResult.setMessage("获取失败");
                return  jsonResult;
            }

            if (jedisToken.equals(token)){
                jsonResult.getItem().put("adminName",admin.getTrueName());
                jsonResult.getItem().put("title",admin.getTitle());
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("获取成功");
                return  jsonResult;
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("获取失败");
                return  jsonResult;
            }


        }
        else
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("获取失败");
            return  jsonResult;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/admin",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult getAdmin(Admin admin, @RequestParam(value = "selectType") String selectType) throws UnsupportedEncodingException {
        byte[] b=admin.getTrueName().getBytes("ISO-8859-1");//用tomcat的格式（iso-8859-1）方式去读。
        String trueName=new String(b,"utf-8");//采用utf-8去接string
        JsonResult jsonResult =adminService.selectAllAdminManage(admin.getUserName(),trueName,admin.getLoginState(),selectType);

        return  jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/admin",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    public JsonResult deleteAdmin(Admin admin){
         JsonResult jsonResult = adminService.deleteAdmin(admin.getId());
        return  jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/updateAdmin",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult getAdmin(Admin admin){
        JsonResult jsonResult = adminService.getAdminInfo(admin.getId());
        return  jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/updateAdmin",method = RequestMethod.POST)
    public JsonResult updateAdmin(Admin admin) {
        JsonResult jsonResult=adminService.updateAdminInfo(admin);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/toAddAdmin",method = RequestMethod.POST)
    public JsonResult addAdmin(Admin admin){
        JsonResult jsonResult = adminService.registerAdmin(admin);
        return  jsonResult;
    }


}

