package cn.lcvc.service;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.AdminPermissions;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    adminPermissions.setMiddle(false);
//     adminPermissions.setHeight(false);
//        admin.setUserName("123456");
        // admin.setUserName("admin_9");
        JsonResult jsonResult=adminPermissionsService.getAdminPermissions(adminPermissions,admin);

        List<AdminPermissions> adminPermissionsList= (List<AdminPermissions>) jsonResult.getItem().get("adminPermissions");

        for (int i=0;i<adminPermissionsList.size();i++){
            System.out.print(adminPermissionsList.get(i).getAdmin().getUserName()+"         ");
            System.out.print(adminPermissionsList.get(i).getAdmin().getTrueName()+"         ");
            System.out.print(adminPermissionsList.get(i).getLow()+"         ");
            System.out.print(adminPermissionsList.get(i).getMiddle()+"         ");
            System.out.println(adminPermissionsList.get(i).getHeight()+"         ");

        }

    }

    /*管理员权限添加*/
    @Test
    public  void registerAdminPermissionsTest(){
        Map<Object,Object> map=new HashMap<Object, Object>();
        Admin admin=new Admin();
        admin.setId(2);
//        map.put("middle",true );
//        map.put("low",true );
        map.put("height",true);
        JsonResult jsonResult=adminPermissionsService.registerAdminPermissions(admin,map);

        System.out.println(jsonResult.getMessage());
    }
}
