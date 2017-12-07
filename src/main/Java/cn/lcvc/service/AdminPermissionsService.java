package cn.lcvc.service;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.AdminPermissions;
import cn.lcvc.dao.AdminDao;
import cn.lcvc.dao.AdminPermissionsDao;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author @wuruibao
 * @Date 2017/12/721:41
 */

@Service
public class AdminPermissionsService {

    @Autowired
    private AdminPermissionsDao adminPermissionsDao;
    @Autowired
    private AdminDao adminDao;


    public JsonResult getAdminPermissions(AdminPermissions adminPermissions, Admin admin){
        JsonResult jsonResult= new JsonResult();
        Map<Object, Object> map = new HashMap<Object, Object>(); //查询所用Map容器
        Map<Object,Object> map_adminPermissions= new HashMap<Object, Object>(); //存放查询所得数据

        if (adminPermissions != null){
           if (adminPermissions.getLow() != null) map.put("low",adminPermissions.getLow());
           if (adminPermissions.getIn() != null) map.put("in",adminPermissions.getIn());
           if (adminPermissions.getHeight() != null) map.put("height",adminPermissions.getHeight());

        }

        if (admin != null){

            if (admin.getUserName() != null && admin.getUserName().trim().length() != 0){
                Admin userName=adminDao.getAdminByUserName(admin.getUserName());//精准搜索
                if (userName != null) map.put("admin",userName);
            }

            if (admin.getTrueName() != null && admin.getTrueName().trim().length() != 0){
                Admin trueName=adminDao.getAdminBy_OneColumn("trueName",admin.getTrueName());//精准搜索
                if (trueName != null) map.put("admin",trueName);
            }

        }

        List<AdminPermissions> adminPermissionsList=adminPermissionsDao.getAdminPermissionsList(AdminPermissions.class,map);

        if (adminPermissionsList.size() > 0){
            map_adminPermissions.put("adminPermissions",adminPermissionsList);

            jsonResult.setErrorCode("200");
            jsonResult.setMessage("查询成功.");
            jsonResult.setItem(map_adminPermissions);
        }else {
            jsonResult.setMessage("500");
            jsonResult.setMessage("无数据.");
        }
        return  jsonResult;
    }





































}
