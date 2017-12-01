package cn.lcvc.service;

import cn.lcvc.POJO.User;
import cn.lcvc.dao.UserDao;
import cn.lcvc.uitl.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void registerUser(User user) {
        user.setSalt(Md5.getRandomString(32));
        user.setUserPassword(Md5.getMD5(user.getUserPassword()+user.getSalt()));
        userDao.addUser(user);
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
