package cn.lcvc.service;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.AdminPermissions;
import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.dao.AdminDao;
import cn.lcvc.dao.AdminPermissionsDao;
import cn.lcvc.uitl.JWT;
import cn.lcvc.uitl.JsonResult;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

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
    @Autowired
    private  AdminService adminService;

    /*
   * 管理员权限管理
   * @param adminPermissions 管理员权限对象
   * @param admin 管理员对象
   * @return
   * */
    public JsonResult getAdminPermissions(AdminPermissions adminPermissions, Admin admin){
        JsonResult jsonResult= new JsonResult();
        Map<Object, Object> map = new HashMap<Object, Object>(); //查询所用Map容器
        Map<Object,Object> map_adminPermissions= new HashMap<Object, Object>(); //存放查询所得数据

        if (adminPermissions != null){
           if (adminPermissions.getLow() != null) map.put("low",true);
           if (adminPermissions.getMiddle() != null) map.put("middle",true);
           if (adminPermissions.getHeight() != null) map.put("height",true);

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
            jsonResult.setList(adminPermissionsList);
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端:无数据.");
        }
        return  jsonResult;
    }

    /*
    * 管理员权限添加 (该功能需要高级管理员操作权限)
    * @param admin 管理员实体
    * @param map:存放权限值 low/middle/height  如对应的key == true,则连同key级别之下aminPermissions表中的字段设置为true,key级别之上的字段属性设置为false (如map中存在 middle键值对，则把low，middle 两个字段设置为true,height设置为false)
    *@param loginAdmin  执行操作的admin，应为已登录admin对象， 判断该admin是否有权执行 权限添加 如果没有则提示无权限，如有则执行对应操作
    * @return 返回Json格式的添加结果，如数据库表已存在改admin实体，则提示不能重复添加，否则进行添加
    * */
    public JsonResult registerAdminPermissions(Admin admin,Admin loginAdmin,Map<Object,Object> map){
        JsonResult jsonResult= new JsonResult();
        AdminPermissions adminPermissions = new AdminPermissions();




        //判断是否有权限执行该操作
        if (loginAdmin != null && loginAdmin.getId() != null){
            //是否存在admin表
            Admin loginAdminPermissions=adminDao.getAdmin(loginAdmin.getId());
            if (loginAdminPermissions != null){
                AdminPermissions getAdminPermissions = adminPermissionsDao.getAdminPermissionsBy_OneColumn("admin",loginAdmin);

                if (!getAdminPermissions.getHeight()){
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端:您没有权限执行该操作!!");
                    return jsonResult;
                }

            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端:账户不存在，请重新登录.");
                return  jsonResult;
            }



        }

        if ( admin != null && admin.getId() != null){
            admin = adminDao.getAdmin(admin.getId());

            if (admin == null){
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("该管理员不存在!!");
                return  jsonResult;
            }

            AdminPermissions old = adminPermissionsDao.getAdminPermissionsBy_OneColumn("admin",admin);
            if ( old !=null){ // 判断是否已经添加权限
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端:权限已存在,请勿重复添加！");
                return  jsonResult;
            }else {
                adminPermissions.setAdmin(admin);
            }
        }

        for (Map.Entry  entry : map.entrySet()) {//遍历map


            if (entry.getKey().equals("low")){
                if (entry.getValue().equals(true)) {
                    adminPermissions.setLow(true);
                    adminPermissions.setMiddle(false);
                    adminPermissions.setHeight(false);
                }else {
                    adminPermissions.setLow((Boolean) entry.getValue());
                }
            }

            if (entry.getKey().equals("middle")){
                if (entry.getValue().equals(true)) {
                    adminPermissions.setLow(true);
                    adminPermissions.setMiddle(true);
                    adminPermissions.setHeight(false);
                }else {
                    adminPermissions.setMiddle((Boolean) entry.getValue());
                }
            }

            if (entry.getKey().equals("height")){
                if (entry.getValue().equals(true)) {
                    adminPermissions.setLow(true);
                    adminPermissions.setMiddle(true);
                    adminPermissions.setHeight(true);
                }else {
                    adminPermissions.setHeight((Boolean) entry.getValue());
                }
            }

        }

        adminPermissionsDao.addAdminPermissions(adminPermissions);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("服务端:添加成功[permissions].");


        return  jsonResult;
    }

    //判断是否具备高级管理员权限
    public Boolean getPermissions(String token) {

        //获取admin登录信息
        if (JWT.verifyJwt(token)) {
            TokenMessage tokenMessage=JWT.getPayloadDecoder(token);
            Admin adminlogin=adminService.getAdmin(tokenMessage.getAdminId());
            String  jedisToken="";
            try {
                Jedis jedis = new Jedis("localhost");
                jedisToken= jedis.get(adminlogin.getId()+"_token");
            }catch (Exception e){
                return  false;
            }

            if (jedisToken.equals(token)){
                AdminPermissions getAdminPermissions = adminPermissionsDao.getAdminPermissionsBy_OneColumn("admin",adminlogin);
                if (!getAdminPermissions.getHeight()){
                    return false;
                }else {
                    return  true;
                }
            }else {
               return false;
            }
        }

        return false;

    }
}
