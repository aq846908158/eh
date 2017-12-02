package cn.lcvc.service;

import cn.lcvc.POJO.User;
import cn.lcvc.dao.UserDao;
import cn.lcvc.uitl.DataCheck;
import cn.lcvc.uitl.JsonResult;
import cn.lcvc.uitl.Md5;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

//注入Service
@Service
public class UserService{
    //注入Dao
    @Autowired
    private  UserDao userDao;

    /**
     * 用户注册
     * @param user 一个用户实体
     */
    public JsonResult registerUser(User user) {
        JsonResult jsonResult=new JsonResult();
        if(user.getUserName().length()<6||user.getUserName().length()>32)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("1数据验证未通过，请刷新页面后重试");
            return jsonResult;
        }
        else if(user.getUserPassword().length()<8||user.getUserPassword().length()>32)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("2数据验证未通过，请刷新页面后重试");
            return jsonResult;
        }
        else if(user.getTrueName().length()<2||user.getTrueName().length()>4)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("3数据验证未通过，请刷新页面后重试");
            return jsonResult;
        }
        else if(user.getSchool()==null||user.getSchool().getId()==0)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("4数据验证未通过，请刷新页面后重试");
            return jsonResult;
        }
        else if(user.getPhone().length()!=11|| !DataCheck.isMobileNO(user.getPhone()))
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("5数据验证未通过，请刷新页面后重试");
            return jsonResult;
        }
        else if(user.getEmail().length()<1||!DataCheck.isEmailNO(user.getEmail()))
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("6数据验证未通过，请刷新页面后重试");
            return jsonResult;
        }
        user.setSalt(Md5.getRandomString(32));
        user.setUserPassword(Md5.getMD5(user.getUserPassword()+user.getSalt()));
        user.setLastTime(new Timestamp(System.currentTimeMillis()));
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        user.setForSaleNumber(0);
        user.setSellNumber(0);
        userDao.addUser(user);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("注册成功");

        return jsonResult;
    }

    /**
     * 删除一个用户
     * @param id 用户的Id
     */
    public void deleteUser(Integer id) {

        if(id!=null&&id!=0) {
                User user=userDao.getUser(id);
                if(user!=null)
                    userDao.deleteUser(user);

        }
    }

    /**
     * 修改一个用户
     * @param user 一个用户实体
     */
    public void updateUser(User user) {
        if (user!=null&&user.getId()!=null)
        {
            userDao.updateUser(user);
        }
    }

    /**
     * 根据Id查询一条用户记录
     * @param id 用户Id
     * @return
     */
    public User getUser(Integer id) {
        return userDao.getUser(id);
    }

    public List<User> getUserList() {
        return  userDao.getUserList();
    }

    public User getUserBy_OneColumn(String column, Object value) {

        return null;
    }

    public User getUserBy_TowColumn(String column1, Object value1, String column2, Object value2) {

        return null;
    }

    public List<User> getUserListOrderBy(String column, String orderBy) {

        return null;
    }

}
