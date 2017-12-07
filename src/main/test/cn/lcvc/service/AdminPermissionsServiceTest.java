package cn.lcvc.service;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.AdminPermissions;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author @wuruibao
 * @Date 2017/12/722:16
 */
public class AdminPermissionsServiceTest extends  BaseJunit {
    @Autowired
    private AdminPermissionsService adminPermissionsService;

    /**管理员权限管理*/
    @Test
    public  void  getAdminPermissionsTest(){
        AdminPermissions adminPermissions = new AdminPermissions();
        Admin admin = new Admin();
//        adminPermissions.setLow(true);
//     adminPermissions.setIn(false);
//     adminPermissions.setHeight(false);
//        admin.setUserName("123456");
        admin.setTrueName("欧阳大");
//       admin.setUserName("admin_1");
        JsonResult jsonResult=adminPermissionsService.getAdminPermissions(adminPermissions,admin);

        List<AdminPermissions> adminPermissionsList= (List<AdminPermissions>) jsonResult.getItem().get("adminPermissions");

        for (int i=0;i<adminPermissionsList.size();i++){
            System.out.print(adminPermissionsList.get(i).getAdmin().getUserName()+"         ");
            System.out.print(adminPermissionsList.get(i).getAdmin().getTrueName()+"         ");
            System.out.print(adminPermissionsList.get(i).getLow()+"         ");
            System.out.print(adminPermissionsList.get(i).getIn()+"         ");
            System.out.println(adminPermissionsList.get(i).getHeight()+"         ");

        }

    }
}
