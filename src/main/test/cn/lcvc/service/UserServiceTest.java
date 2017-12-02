package cn.lcvc.service;

import cn.lcvc.POJO.School;
import cn.lcvc.POJO.User;
import cn.lcvc.uitl.JsonResult;
import cn.lcvc.uitl.Md5;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class UserServiceTest extends BaseJunit{
    @Autowired
    private  UserService userService;

    @Test
    public void addUser() throws Exception {
       User user =new User();
        School school=new School();
        school.setId(1);
       user.setEmail("599999@qq.com");
       user.setPhone("13999999999");
       user.setSchool(school);
       user.setTrueName("哈哈浏览");
       user.setUserName("user1111");
       user.setUserPassword("12345678");
       JsonResult jsonResult=userService.registerUser(user);
        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }//通过
    @Test
    public void deleteUser() throws Exception {

        userService.deleteUser(1);
    }//通过


    @Test
    public void baseMessageUpdate() throws Exception {
        School school=new School();
        school.setId(2);
        User user=userService.getUser(3);
        user.setTrueName("我日日啊");
        user.setPhone("13888888888");
        user.setEmail("999999999@qq.com");
        user.setSchool(school);
        JsonResult jsonResult= userService.baseMessageUpdate(user);
        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void login() throws Exception {
        JsonResult jsonResult=userService.login("user1111","12345678");
        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void updateUserPassWord() throws Exception {
        JsonResult jsonResult=userService.updateUserPassword(8,"12345678","87654321");
        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void updateBanLogin() throws Exception
    {
        JsonResult jsonResult=userService.updateBanLogin(8,false);
        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void updateBanSell() throws Exception
    {
        JsonResult jsonResult=userService.updateBanSell(8,true);
        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void updateSellNumber() throws Exception
    {
        JsonResult jsonResult=userService.updateSellNumber(8,10);
        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void updateForSaleNumber() throws Exception
    {
        JsonResult jsonResult=userService.updateForSaleNumber(8,10);
        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }//通过
}