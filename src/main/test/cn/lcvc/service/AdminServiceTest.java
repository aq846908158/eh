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
    public void addAdmin() throws  Exception{
        Admin admin = new Admin();

        admin.setUserName("admin_8");
        admin.setTrueName("测试姓名");
        admin.setEmail("10775@sina.cn");
        JsonResult jsonResult=adminService.registerAdmin(admin);

        System.out.println(jsonResult.getErrorCode());
        System.out.println(jsonResult.getMessage());
    }

    /*管理员登录功能测试成功*/
    @Test
    public  void loginAdmin() throws  Exception{
        JsonResult jsonResult=adminService.login("admin_8","admin_8");
        Admin admin= (Admin) jsonResult.getItem().get("admin");
        System.out.println(admin.getEmail());
        System.out.println(jsonResult.getMessage());

    }
}
