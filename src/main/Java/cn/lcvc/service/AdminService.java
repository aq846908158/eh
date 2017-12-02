package cn.lcvc.service;

import cn.lcvc.POJO.Admin;
import cn.lcvc.dao.AdminDao;
import cn.lcvc.uitl.DataCheck;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD2;
import sun.security.provider.MD5;
import cn.lcvc.uitl.Md5;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    /*
    * 管理员登录
    * @param username 账户名
    * @param pasword 密码
    * */
    public JsonResult login(String username,String password ){

        JsonResult jsonResult=new JsonResult();
        Map<Object,Object> map= new HashMap<Object, Object>();

        if (username == null || username.length() == 0){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请输入账户名.");
            return  jsonResult;
        }

        if(password == null || password.length() == 0){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请输入密码.");
            return  jsonResult;
        }
        //获取登录对象
        Admin  admin=adminDao.getAdminByUserName(username);
        if (admin != null){
            String salt=admin.getSalt();
            String loginPassword = password.concat(salt);
            String  md5PasswordOfLogin = Md5.MD5(loginPassword);
            if (md5PasswordOfLogin.equals(admin.getUserPassword()) && username.equals(admin.getUserName())){//验证密码
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("登录成功!");
                map.put("admin",admin);
                jsonResult.setItem(map);
            }else{
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("密码错误，请重试.");
            }

        }else{
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("账户不存在，请重试.");
        }


        return jsonResult;
    }

    /*
 * 添加管理员
 * @param admin 管理员实体
 *
 * */
    public JsonResult  registerAdmin(Admin admin) {
        JsonResult jsonResult = new JsonResult();
        Admin admin_username=adminDao.getAdminByUserName(admin.getUserName());
        if (admin_username != null){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("账户名已存在.请重试.");
            return jsonResult;
        }
        if (admin.getUserName() != null) {
            if (admin.getUserName().length() < 6 || admin.getUserName().length() > 32) {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("账户名长度在6-32位之间.");
                return jsonResult;
            }
        } else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请输入账户名.");
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
            if (admin.getTrueName().length() <  1 || admin.getTrueName().length() > 4) {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("真实姓名长度在1-4位之间.");
                return jsonResult;
            }
        } else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请输入真实姓名.");
            return jsonResult;
        }

        if (admin.getEmail() != null) {

            if (DataCheck.isEmailNO(admin.getEmail())) {
                if (admin.getEmail().length() < 8 || admin.getEmail().length() > 30) {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("邮箱长度8-30位之间.");
                    return jsonResult;
                }
            } else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("请输入正确Email格式.");
                return jsonResult;
            }

        } else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请输入Email.");
            return jsonResult;
        }

        String salt=Md5.getRandomString(32);//盐值
        String password=admin.getUserName().concat(salt);
        String md5password=Md5.MD5(password);//默认密码为登录账户名.

        admin.setSalt(salt);//添加盐值
        admin.setUserPassword(md5password);//默认密码
        admin.setCreateTime(new Timestamp(System.currentTimeMillis()));//创建时间

        adminDao.addAdmin(admin);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("添加成功.");
            return jsonResult;

    }





















}
