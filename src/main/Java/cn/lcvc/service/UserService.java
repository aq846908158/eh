package cn.lcvc.service;

import cn.lcvc.POJO.User;
import cn.lcvc.dao.SchoolDao;
import cn.lcvc.dao.UserDao;
import cn.lcvc.uitl.DataCheck;
import cn.lcvc.uitl.JsonResult;
import cn.lcvc.uitl.Md5;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//注入Service
@Service
public class UserService{
    //注入Dao
    @Autowired
    private  UserDao userDao;
    @Autowired
    private SchoolDao schoolDao;

    /**
     * 用户注册
     * @param user 一个用户实体
     */
    public JsonResult registerUser(User user) {
        JsonResult jsonResult=new JsonResult();
        if(userDao.getUserBy_OneColumn("userName",user.getUserName())!=null)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("用户名已存在");
            return jsonResult;
        }

        if(user.getUserName().length()<6||user.getUserName().length()>32)
        {

            jsonResult.setMessage("用户名格式错误!应在6-32字符以内");
            return jsonResult;
        }
        else if(user.getUserPassword().length()<8||user.getUserPassword().length()>32)
        {
            jsonResult.setErrorCode("500");
                jsonResult.setMessage("密码格式错误!应在8-32字符以内");
            return jsonResult;
        }
        else if(!DataCheck.isTrueName(user.getTrueName()))
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("真实姓名应该在2-4个中文字符以内");
            return jsonResult;
        }
        else if(user.getSchool()==null||user.getSchool().getId()==0)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("请选择所在校园");
            return jsonResult;
        }
        else if(user.getPhone().length()!=11|| !DataCheck.isMobileNO(user.getPhone()))
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("手机号码格式不正确");
            return jsonResult;
        }
        else if(user.getEmail().length()<1||!DataCheck.isEmailNO(user.getEmail()))
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("邮箱格式不正确");
            return jsonResult;
        }


        user.setSalt(Md5.getRandomString(32));
        user.setUserPassword(Md5.MD5(user.getUserPassword()+user.getSalt()));
        user.setLastTime(new Timestamp(System.currentTimeMillis()));
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        user.setForSaleNumber(0);
        user.setSellNumber(0);
        user.setBanLogin(false);
        user.setBanSell(false);
        userDao.addUser(user);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("注册成功");

        return jsonResult;
    }//完成

    /**
     * 删除一个用户
     * @param id 用户的Id
     */
    public JsonResult deleteUser(Integer id) {
        JsonResult jsonResult=new JsonResult();
        if(id!=null&&id!=0) {
                User user=userDao.getUser(id);
                if(user!=null) {
                    userDao.deleteUser(user);
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("删除成功");
                    return  jsonResult;
                }else{
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("数据库中不存在此用户");
                    return jsonResult;
                }

        }

        jsonResult.setErrorCode("500");
        jsonResult.setMessage("删除失败");
        return jsonResult;

    }//完成

    /**
     * 修改一个用户的基本信息
     * @param user 一个用户实体
     * 注意!此方法只能对基本信息进行表单验证！特殊字段修改请不要使用此方法
     */
    public JsonResult baseMessageUpdate(User user) {
        JsonResult jsonResult=new JsonResult();
        if (user!=null && user.getId()!=null && userDao.getUser(user.getId())!=null)
        {

            if(!DataCheck.isTrueName(user.getTrueName()))
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("真实姓名应该在2-4个中文字符以内");
                return jsonResult;
            }
            else if(user.getSchool()==null||user.getSchool().getId()==0)
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("请选择所在校园");
                return jsonResult;
            }
            else if(schoolDao.getSchool(user.getSchool().getId())==null)
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("数据库中没有此校园");
                return jsonResult;
            }
            else if(user.getPhone().length()!=11|| !DataCheck.isMobileNO(user.getPhone()))
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("手机号码格式不正确");
                return jsonResult;
            }
            else if(user.getEmail().length()<1||!DataCheck.isEmailNO(user.getEmail()))
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("邮箱格式不正确");
                return jsonResult;
            }

                userDao.updateUser(user);
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("修改成功");
                return jsonResult;


        }

        jsonResult.setErrorCode("500");
        jsonResult.setMessage("修改失败");
        return  jsonResult;
    }//完成

    /**
     * 用户登录功能,判断用户名和密码是否正确、用户是否被封号,成功后修改最后登录时间
     * @param userName 用户名
     * @param userPassword  密码
     * @return JsonResult数据 如果登录成功（JsonResult.item）中会存放key为“user”的一个User对象用于让控制层存入Session
     */
    public  JsonResult login(String userName,String userPassword)
    {
        JsonResult jsonResult=new JsonResult();
        User user=userDao.getUserBy_OneColumn("userName",userName);
        if(user!=null && user.getBanLogin()!=true)
        {
            userPassword=Md5.MD5(userPassword+user.getSalt());

            if(user.getUserPassword().equals(userPassword))
            {
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("登录成功");
                //修改最后登录时间
                user.setLastTime(new Timestamp(System.currentTimeMillis()));
                userDao.updateUser(user);
                //将user对象传到控制层 以便于登录成功后在控制层中将登录成功的用户对象存入Session
                Map<Object,Object> temp=new HashMap<Object,Object>();
                temp.put("user",user);

                return  jsonResult;
            }
            else
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("用户名或密码输入错误");
                return  jsonResult;
            }
        }
        else{
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("用户被禁止登录!");
            return  jsonResult;
        }

    }//完成

    /**
     * 修改用户密码
     * @param id 用户Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return JsonResult数据 如果修改成功 （JsonResult.message="修改成功"） 如果修改失败（JsonResult.message=错误信息）
     */
    public  JsonResult updateUserPassword(Integer id,String oldPassword,String newPassword)
    {
        JsonResult jsonResult=new JsonResult();
        User user=userDao.getUser(id);

        if(user!=null)
        {
            oldPassword=Md5.MD5(oldPassword+user.getSalt());
            if(user.getUserPassword().equals(oldPassword))
            {
                if(newPassword.length()<8||newPassword.length()>32)
                {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("密码格式错误!应在8-32字符以内");
                    return jsonResult;
                }
                else
                {
                    newPassword=Md5.MD5(newPassword+user.getSalt());
                    user.setUserPassword(newPassword);
                    userDao.updateUser(user);
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("密码修改成功");
                    return jsonResult;
                }
            }
            else
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("旧密码输入错误");
                return jsonResult;
            }

        }
        else
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("密码修改失败");
            return jsonResult;
        }

    }//完成

    /**
     * 修改用户禁止登录状态
     * @param id 用户ID
     * @param banNo 需要将用户禁止登录状态设置成为哪种状态
     * @return JsonResult对象 如若修改成功 （JsonResult.message="修改成功"）如若修改失败 （JsonResult.message=错误信息）
     */
    public JsonResult updateBanLogin(Integer id,Boolean banNo)
    {
        JsonResult jsonResult=new JsonResult();
        User user=userDao.getUser(id);
        if(user!=null)
        {
            user.setBanLogin(banNo);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("修改成功");
            return  jsonResult;
        }
        jsonResult.setErrorCode("500");
        jsonResult.setMessage("修改失败");
        return  jsonResult;
    }//完成

    /**
     * 修改用户禁止发布商品状态
     * @param id 用户ID
     * @param banNo 需要将用户禁止发布商品状态设置成为哪种状态
     * @return JsonResult对象 如若修改成功 （JsonResult.message="修改成功"）如若修改失败 （JsonResult.message=错误信息）
     */
    public JsonResult updateBanSell(Integer id,Boolean banNo)
    {
        JsonResult jsonResult=new JsonResult();
        User user=userDao.getUser(id);
        if(user!=null)
        {
            user.setBanSell(banNo);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("修改成功");
            return  jsonResult;
        }
        jsonResult.setErrorCode("500");
        jsonResult.setMessage("修改失败");
        return  jsonResult;
    }//完成

    /**
     * 修改用户售出商品总数
     * @param id 用户Id
     * @param number 需要修改的数量
     * @return JsonResult对象 如若修改成功 （JsonResult.message="修改成功"）如若修改失败 （JsonResult.message=错误信息）
     */
    public JsonResult updateSellNumber(Integer id,Integer number)
    {
        JsonResult jsonResult=new JsonResult();
        User user=userDao.getUser(id);
        if(user==null)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("修改失败");
            return  jsonResult;
        }

        user.setSellNumber(number);
        userDao.updateUser(user);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("修改成功");
        return  jsonResult;
    }//完成


    /**
     * 修改用户在售商品总数
     * @param id 用户Id
     * @param number 需要修改的数量
     * @return JsonResult对象 如若修改成功 （JsonResult.message="修改成功"）如若修改失败 （JsonResult.message=错误信息）
     */
    public JsonResult updateForSaleNumber(Integer id,Integer number)
    {
        JsonResult jsonResult=new JsonResult();
        User user=userDao.getUser(id);
        if(user==null)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("修改失败");
            return  jsonResult;
        }

        user.setForSaleNumber(number);
        userDao.updateUser(user);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("修改成功");
        return  jsonResult;
    }//完成

    public User getUser(Integer id)
    {
        return  userDao.getUser(id);
    }




}
