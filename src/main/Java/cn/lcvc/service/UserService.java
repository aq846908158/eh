package cn.lcvc.service;

import cn.lcvc.POJO.User;
import cn.lcvc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//注入Service
@Service
public class UserService{
    //注入Dao
    @Autowired
    private  UserDao userDao;


    public void addUser(User user) {
        userDao.addUser(user);
    }

    public void deleteUser(User user) {
        if(user!=null&&user.getId()!=null) {
            if (getUser(user.getId())!=null)
                userDao.deleteUser(user);
        }
    }

    public void updateUser(User user) {
        if (user!=null&&user.getId()!=null)
        {
            userDao.updateUser(user);
        }
    }

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
