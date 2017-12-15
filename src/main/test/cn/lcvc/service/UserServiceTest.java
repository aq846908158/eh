package cn.lcvc.service;

import cn.lcvc.POJO.School;
import cn.lcvc.POJO.User;
import cn.lcvc.uitl.JWT;
import cn.lcvc.uitl.JsonResult;
import cn.lcvc.uitl.Md5;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import sun.nio.cs.US_ASCII;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
       user.setUserName("user2222");
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
        Jedis jedis=new Jedis("localhost");
        JsonResult jsonResult=userService.login("user1111","12345678");
        String userId=jsonResult.getItem().get("userId")+"";
        String token=jedis.get(userId+"_token");
        System.out.println(token);
        System.out.println(JWT.verifyJwt(token));
    }//通过x

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
    public void getAllUser() throws Exception
    {
        JsonResult jsonResult=userService.getAllUser();
        List<User> users= (List<User>) jsonResult.getItem().get("users");
        System.out.println(users.size());

    }//通过

    @Test
    public void updateForSaleNumber() throws Exception
    {
        JsonResult jsonResult=userService.updateForSaleNumber(8,10);
        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }//通过


    // 100%
    @Test
    public void getAllUserManageTest() throws  Exception{
        User user=new User();

       // user.setUserName("use");
//        user.setTrueName("哈");
//        user.setBanLogin(true);
     //   user.setBanSell(false);
        JsonResult jsonResult = userService.getAllUserManage(user,1,10,1,2);

        List<User> users = (List<User>) jsonResult.getItem().get("users");

        for (int i=0;i<users.size();i++){
            System.out.println(users.get(i).getUserName());
            System.out.println(users.get(i).getTrueName());
            System.out.println(users.get(i).getBanLogin());

        }
    }
    /*用户登录封号  75%*/
    @Test
    public  void setUserSealTest(){
        JsonResult jsonResult =userService.setUserBanLogin(20,false);

        System.out.println(jsonResult.getMessage());
    }

    /*用户交易封号  75%*/
    @Test
    public  void setUserBanSellTest(){
        JsonResult jsonResult =userService.setUserBanSell(-13,false);

        System.out.println(jsonResult.getMessage());
    }

}