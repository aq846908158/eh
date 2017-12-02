package cn.lcvc.service;

import cn.lcvc.POJO.School;
import cn.lcvc.POJO.User;
import cn.lcvc.uitl.JsonResult;
import cn.lcvc.uitl.Md5;
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
       user.setTrueName("哈哈哈哈");
       user.setUserName("user11");
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
    public void updateUser() throws Exception {
        User user=userService.getUser(2);
        user.setTrueName("我擦擦");
        userService.updateUser(user);
    }//通过

    @Test
    public void getUser() throws Exception {
    }

    @Test
    public void getUserList() throws Exception {
    }

}