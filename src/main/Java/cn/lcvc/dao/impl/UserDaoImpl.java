package cn.lcvc.dao.impl;

import cn.lcvc.POJO.User;
import cn.lcvc.dao.UserDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDaoImpl implements UserDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addUser(User user) {

    }

    public void deleteUser(User user) {

    }

    public void updateUser(User user) {

    }

    public User getUser(Integer id) {
        return null;
    }

    public List<User> getUserList() {
        String hql="from  User ";
        Query query=getSession().createQuery(hql);
        return query.list();
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
