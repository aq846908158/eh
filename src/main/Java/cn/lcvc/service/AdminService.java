package cn.lcvc.service;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.AdminPermissions;
import cn.lcvc.dao.AdminDao;
import cn.lcvc.dao.AdminPermissionsDao;
import cn.lcvc.uitl.DataCheck;
import cn.lcvc.uitl.JWT;
import cn.lcvc.uitl.JsonResult;
import cn.lcvc.uitl.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *@Author @wuruibao
 *@Date 2017/12/2 22:14
*/
@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminPermissionsDao adminPermissionsDao;

    public Admin getAdmin(Integer id)
    {
        Admin admin=null;
        if(id!=null&&id!=0) {
          admin  = adminDao.getAdmin(id);
        }
        return  admin;
    }


    /*
    * 管理员登录
    * @param username 账户名
    * @param pasword 密码
    * */
    public JsonResult login(String username, String password ){

        JsonResult jsonResult=new JsonResult();
        Map<Object,Object> map= new HashMap<Object, Object>();

        if (username == null || username.trim().length() == 0){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请输入账户名");
            return  jsonResult;
        }

        if(password == null || password.trim().length() == 0){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请输入密码");
            return  jsonResult;
        }
        //获取登录对象
        Admin admin=adminDao.getAdminByUserName(username.trim());
        if (admin != null ){
            if (admin.getLoginState() == 1){
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端：对不起，您被禁止登录");
                return jsonResult;
            }
            String salt=admin.getSalt();
            String loginPassword = password.concat(salt);
            String  md5PasswordOfLogin = Md5.MD5(loginPassword.trim());
            if (md5PasswordOfLogin.equals(admin.getUserPassword()) && username.equals(admin.getUserName())){//验证密码

                String token = "";  //存token
                try {
                    token = JWT.cretaTokenOfAdmin(admin);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                map.put("token",token);
                map.put("adminId",admin.getId());
                Jedis jedis = new Jedis("localhost");//链接本地Redis
                jedis.set(admin.getId()+"_token",token);//tonken存入Redis
                jsonResult.setItem(map);
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("登录成功");
                jsonResult.setItem(map);
            }else{
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("密码错误，请重试");
            }

        }else{
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("账户不存在，请重试");
        }


        return jsonResult;
    }

    /*
 * 添加管理员
 * @param admin 管理员实体
 *
 * */
    public JsonResult registerAdmin(Admin admin) {
        JsonResult jsonResult = new JsonResult();

        Admin admin_username=new Admin();
        if (admin.getUserName() != null || admin.getUserName().trim().length() !=0){
             admin_username=adminDao.getAdminByUserName(admin.getUserName().trim());
        }

        if (admin_username != null){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：账户名已存在.请重试");
            return jsonResult;
        }

        if (admin.getUserName() == null || admin.getUserName().length() == 0) {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：请输入账户名");
            return jsonResult;

        } else if (admin.getUserName().trim().length() < 6 || admin.getUserName().trim().length() > 32) {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：账户名长度在6-32位之间");
            return jsonResult;
        }

      /*  if (admin.getUserPassword() != null) {
            if (admin.getUserPassword().length() <= 8 || admin.getUserPassword().length() >= 32) {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("密码长度在8-32位之间.");
                return jsonResult;
            }
        } else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请输入密码.");
            return jsonResult;
        }*/

        if (admin.getTrueName() != null) {
            if (DataCheck.isTrueName(admin.getTrueName())){
                if (admin.getTrueName().trim().length() <  2 || admin.getTrueName().trim().length() > 4) {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端：真实姓名长度在1-4位之间.");
                    return jsonResult;
                }
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端：请输入真实姓名.");
                return jsonResult;
            }

        } else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：请输入真实姓名.");
            return jsonResult;
        }

      /*  if (admin.getEmail() != null) {

            if (DataCheck.isEmailNO(admin.getEmail())) {
                if (admin.getEmail().trim().length() < 8 || admin.getEmail().trim().length() > 30) {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端：邮箱长度8-30位之间.");
                    return jsonResult;
                }
            } else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端：请输入正确Email格式.");
                return jsonResult;
            }

        } else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：请输入Email.");
            return jsonResult;
        }*/

        String salt= Md5.getRandomString(32);//盐值
        String password=admin.getUserName().concat(salt);
        String md5password= Md5.MD5(password);//默认密码为登录账户名.

        admin.setSalt(salt);//添加盐值
        admin.setUserPassword(md5password);//默认密码
        admin.setCreateTime(new Timestamp(System.currentTimeMillis()));//创建时间
        admin.setLastTime(new Timestamp(System.currentTimeMillis()));//最后登录时间
        admin.setLoginState(0);
        admin.setLoginNum(0);
        admin.setTitle(null);

        adminDao.addAdmin(admin);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("服务端：添加成功.");
            return jsonResult;

    }

    /*修改当前登录管理员密码
     *@Author @wuruibao
     *@Date 2017/12/2 22:22
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     *@return
    */
    public JsonResult updateAdminPassword(Admin admin, String oldPassword, String newPassword){
        JsonResult jsonResult = new JsonResult();

        Admin admin1=adminDao.getAdmin(admin.getId());

         oldPassword= Md5.MD5(oldPassword.concat(admin1.getSalt()));//旧密码+盐值

        if (!admin1.getUserPassword().equals(oldPassword)) {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("旧密码错误.请重试");
            return jsonResult;
        }

        String salt= Md5.getRandomString(32);//盐值
        String password=newPassword.concat(salt);
        String md5password= Md5.MD5(password);

        admin1.setSalt(salt);
        admin1.setUserPassword(md5password);

        adminDao.updateAdmin(admin1);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("修改成功.");
        return  jsonResult;
    }


    /**
     *
     * @param id
     * @return
     */
    public JsonResult getAdminInfo(Integer id){
       JsonResult jsonResult = new JsonResult();
        Map<Object,Object> map_admin= new HashMap<Object, Object>(); //存放查询所得数据
        String sql="SELECT id,userName,trueName,phone,email FROM Admin  WHERE id="+id;//使用SQL原生查询
        Admin admin=adminDao.getAdminInfo(sql);
        if (admin != null){
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("查询成功.");
            map_admin.put("admin",admin);
            jsonResult.setItem(map_admin);
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：无数据.");
        }

        return  jsonResult;
    }

    /*修改管理员个人信息
     *@Author @wuruibao
     *@Date 2017/12/2 22:51
     * @param admin   管理员实体
     *@return jsonResult：修改结果
    */
    public JsonResult updateAdminInfo(Admin admin){
        JsonResult jsonResult = new JsonResult();

        /*本方法假设所有字段通过验证,验证将在controller实现*/

        if (admin !=null){
            if (admin.getTrueName() != null) {
                if (DataCheck.isTrueName(admin.getTrueName())){
                    if (admin.getTrueName().trim().length() <  2 || admin.getTrueName().trim().length() > 4) {
                        jsonResult.setErrorCode("500");
                        jsonResult.setMessage("服务端：真实姓名长度在1-4位之间.");
                        return jsonResult;
                    }
                }else {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端：请输入真实姓名.");
                    return jsonResult;
                }

            } else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端：请输入真实姓名.");
                return jsonResult;
            }


            if (admin.getPhone() != null){
                if (admin.getPhone().trim().length() != 11){
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端：请输入正确的手机号.");
                    return jsonResult;
                }
            }

            if (admin.getEmail() != null) {
                if (DataCheck.isEmailNO(admin.getEmail())) {
                    if (admin.getEmail().trim().length() < 8 || admin.getEmail().trim().length() > 30) {
                        jsonResult.setErrorCode("500");
                        jsonResult.setMessage("服务端：邮箱长度8-30位之间.");
                        return jsonResult;
                    }
                } else {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端：请输入正确Email格式.");
                    return jsonResult;
                }

            } else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端：请输入Email.");
                return jsonResult;
            }


        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：无法修改.请刷新后重试.");
            return  jsonResult;
        }


        if (adminDao.getAdminByUserNameInId(admin).size() >0) {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：账户名已存在.请重试.");
        }else {
            Admin oldadmin = adminDao.getAdmin(admin.getId());
           // oldadmin.setUserName(admin.getUserName().trim());不允许修改用户名
            oldadmin.setTrueName(admin.getTrueName().trim());
            oldadmin.setPhone(admin.getPhone().trim());
            oldadmin.setEmail(admin.getEmail().trim());

            adminDao.updateAdmin(oldadmin);

            jsonResult.setErrorCode("200");
            jsonResult.setMessage("修改成功.");

        }

        return  jsonResult;
    }


    /**
     * 管理员管理,如果username与truename为空，则查询所有管理员，否则根据条件查询
     *@Author @wuruibao
     *@Date 2017/12/3 14:46
     *@params    userNmae:账户名；truneName：真实姓名,selectType:查询类型,(like:模糊查询,eq:精准查询)
     *@return 将list集合放入jsonResult的Map集合里，返回到前台
    */
    public JsonResult selectAllAdminManage(String userNmae, String trueName, Integer loginState, String selectType){
     JsonResult jsonResult = new JsonResult();
     Map<String,Object>  map = new HashMap<String, Object>(); //查询所用Map容器
     Map<Object,Object> map_admin= new HashMap<Object, Object>(); //存放查询所得数据


      if(userNmae != null && userNmae.trim().length() != 0) map.put("userName", userNmae.trim());
      if(trueName != null && trueName.trim().length() != 0) map.put("trueName", trueName.trim());
      if (loginState != null && loginState != -1 && loginState >= 0 && loginState <= 1) map.put("loginState",loginState);
      if (selectType != null && selectType.trim().length() != 0) map.put("seltype",selectType.trim());

        List<Admin> list=adminDao.queryAllAdminManage(Admin.class,map);
        if (list.size() > 0) {
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("查询成功.");
            jsonResult.setList(list);
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("无数据.");
        }

     return  jsonResult;
    }


    /**
     *删除管理员
     *@Author @wuruibao
     *@Date 2017/12/3 16:10
     *@params   id:所需删除id
     *@return 删除结果
    */
    public JsonResult deleteAdmin(Integer id){
        JsonResult jsonResult = new JsonResult();
        if (id != null) {
            Admin admin=adminDao.getAdmin(id);
            if (admin != null) {
                AdminPermissions adminPermissions =adminPermissionsDao.getAdminPermissionsBy_OneColumn("admin",admin); // 查询该admin是否存在于数据库
                if (adminPermissions != null){
                    adminPermissionsDao.deleteAdminPermissions(adminPermissions);
                }
                adminDao.deleteAdmin(admin);//删除前先清除admin权限表外键
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端：删除异常.没有数据");
                return  jsonResult;
            }
            if (adminDao.getAdmin(id) == null){
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("删除成功.");
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("删除失败.");
            }
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("删除异常.");
        }



        return  jsonResult;
    }

    /**
     *管理员密码重置
     *@Author @wuruibao
     *@Date 2017-12-5 10:41:12
     *@params   id:所需重置id
     *@return  返回重置结果
     */
    public JsonResult resetAdminPassword(Integer id){
        JsonResult jsonResult = new JsonResult();
        if (id != null){
            Admin admin=adminDao.getAdmin(id);
            if (admin != null){
                String salt= Md5.getRandomString(32);//从新生成盐值
                String password=admin.getUserName().concat(salt);//账户名+盐值 组合成字符串
                String md5password= Md5.MD5(password);//重置密码为 登录账户名.

                admin.setSalt(salt);
                admin.setUserPassword(md5password);

                adminDao.updateAdmin(admin);

                jsonResult.setErrorCode("200");
                jsonResult.setMessage("重置成功,新密码为该账户名.");
            }else{
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("重置失败,账户不存在");
                return  jsonResult;
            }

        }else{
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("重置失败,账户为空");
        }

        return  jsonResult;
    }

    public JsonResult updateAdminLoginState(Integer id,Integer stateCode){
        JsonResult jsonResult =new JsonResult();
        Admin admin=null;
        if (stateCode >= 0 || stateCode <= 1){
             admin =adminDao.getAdmin(id);
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：修改失败，请刷新后重试");
            return  jsonResult;
        }
        if (admin !=null){
            if (admin.getLoginState() == stateCode){
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("服务端：修改成功");
                return  jsonResult;
            }else {
                admin.setLoginState(stateCode);
                adminDao.updateAdmin(admin);
                Admin adminNew=adminDao.getAdmin(admin.getId());
                if (adminNew.getLoginState() == stateCode){
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("服务端：修改成功");
                    return  jsonResult;
                }else {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端：修改失败，请刷新后重试");
                    return  jsonResult;
                }

            }

        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：修改状态失败，请重试");
            return  jsonResult;
        }

    }







}
