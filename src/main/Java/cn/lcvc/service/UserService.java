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



    public List<User> getAllUser() {
        return userDao.getUserList();
    }

}
