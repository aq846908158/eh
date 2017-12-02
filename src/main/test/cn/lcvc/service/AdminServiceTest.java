package cn.lcvc.service;

import cn.lcvc.POJO.Admin;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminServiceTest extends  BaseJunit {
    @Autowired
    private AdminService adminService;

    /*添加管理员功能测试成功*/
    @Test
    public void addAdminTest() throws  Exception{
        Admin admin = new Admin();

        admin.setUserName("admin_9");
        admin.setTrueName("A3.");
        admin.setEmail("10775@sina.cn");
        JsonResult jsonResult=adminService.registerAdmin(admin);

        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }

    /*管理员登录功能测试成功*/
    @Test
    public  void loginAdminTest() throws  Exception{
        JsonResult jsonResult=adminService.login("admin","admin7792");
        Admin admin= (Admin) jsonResult.getItem().get("admin");
        System.out.println(admin.getEmail());
        System.out.println(jsonResult.getMessage());

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
        admin.setUserName("admin");
        admin.setTrueName("伍锐保");
        admin.setPhone("18269652102");
        admin.setEmail("1056042624@qq.com");
        JsonResult jsonResult=adminService.updateAdminInfo(admin);
        System.out.println(jsonResult.getMessage());
    }

}
