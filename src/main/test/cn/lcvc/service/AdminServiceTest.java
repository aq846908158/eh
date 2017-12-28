package cn.lcvc.service;

import cn.lcvc.POJO.Admin;
import cn.lcvc.uitl.JWT;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

public class AdminServiceTest extends  BaseJunit {
    @Autowired
    private AdminService adminService;

    /*添加管理员功能测试成功*/
    @Test
    public void addAdminTest() throws  Exception{
        Admin admin = new Admin();

        admin.setUserName("admin_wu");
        admin.setTrueName("蛇皮伍");
        admin.setEmail("10775@sina.cn");
        admin.setTitle("中级");
        JsonResult jsonResult=adminService.registerAdmin(admin);

        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }

    /*管理员登录功能测试成功*/
    @Test
    public  void loginAdminTest() throws  Exception{
        Jedis jedis = new Jedis("localhost");
      //  JsonResult jsonResult=adminService.login("admin_wu","admin_wu");
      //  String admiId=jsonResult.getItem().get("adminId")+"";
      //  String  admin_token= (String) jsonResult.getItem().get("token");//前台token
        String token=jedis.get(7+"_token");//redis的token

      //  if (admin_token.equals(token)) System.out.println("验证成功");
        System.out.println(JWT.verifyJwt(token));
       // System.out.println(jsonResult.getMessage());
        System.out.println(token);

    }

    /*管理员密码修改 50%*/
    @Test
    public void updateAdminPasswordTest(){
        Admin admin=new Admin();
        admin.setId(6);

        JsonResult jsonResult=adminService.updateAdminPassword(admin,"admin_8","admin7792");
        System.out.println(jsonResult.getMessage());
    }

    /*修改管理员个人信息 50%*/
    @Test
    public  void  updateAdminInfoTest(){
        Admin admin=new Admin();
        admin.setId(6);
        admin.setUserName("admin_1");
        admin.setTrueName("伍锐保");
        admin.setPhone("18269652102");
        admin.setEmail("1056042624@qq.com");
        JsonResult jsonResult=adminService.updateAdminInfo(admin);
        System.out.println(jsonResult.getMessage());
    }

    /*管理员管理 75%*/
    @Test
    public  void  seleteAllAdminManageTest(){
        /*JsonResult jsonResult=adminService.selectAllAdminManage("","","");
        List<Admin> adminList= (List<Admin>) jsonResult.getItem().get("admin");
        System.out.println(jsonResult.getMessage());

        for(int i = 0 ; i < adminList.size() ; i++) {
            System.out.println(adminList.get(i).getUserName());
        }*/

    }


    /*删除管理员 100%*/
    @Test
    public  void deleteAdminTest(){
        JsonResult jsonResult=adminService.deleteAdmin(40);
        System.out.println(jsonResult.getMessage());
    }

    /**管理员密码重置   100%*/
    @Test
    public void resetAdminPasswordTest(){
        JsonResult jsonResult=adminService.resetAdminPassword(3);
        System.out.println(jsonResult.getMessage());
    }







}
