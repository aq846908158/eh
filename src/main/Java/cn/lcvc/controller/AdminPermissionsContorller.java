package cn.lcvc.controller;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.AdminPermissions;
import cn.lcvc.service.AdminPermissionsService;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author @wuruibao
 * @Date 2018/1/123:23
 */
@Controller
@RequestMapping(value = "adminPermissions")
public class AdminPermissionsContorller {

    @Autowired
    private AdminPermissionsService adminPermissionsService;

    @ResponseBody
    @RequestMapping(value = "adminPermissions",method = RequestMethod.GET)
    public JsonResult getAdminPermissions(){
        AdminPermissions adminPermissions=new AdminPermissions();
        Admin admin=new Admin();
        JsonResult jsonResult = adminPermissionsService.getAdminPermissions(adminPermissions,admin);

        return  jsonResult;
    }
}
