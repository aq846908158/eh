package cn.lcvc.dao.impl;

import cn.lcvc.POJO.User;
import cn.lcvc.dao.UserDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.OrderBy;
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
        getSession().save(user);
    }

    public void deleteUser(User user) {
        getSession().delete(getSession().load(User.class,user.getId()));
    }

    public void updateUser(User user) {
        User oldUser= (User) getSession().load(User.class,user.getId());
        oldUser.setSchool(user.getSchool());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhone(user.getPhone());
        oldUser.setUserName(user.getUserName());
        oldUser.setUserPassword(user.getUserPassword());
        oldUser.setTrueName(user.getTrueName());
        oldUser.setLastTime(user.getLastTime());
        oldUser.setSellNumber(user.getSellNumber());
        oldUser.setForSaleNumber(user.getForSaleNumber());
        oldUser.setSalt(user.getSalt());
        getSession().update(oldUser);

    }

    public User getUser(Integer id) {
        Criteria criteria=getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (User)list.get(0);
        }
        return null;
    }

    public List<User> getUserList() {
        Criteria criteria=getSession().createCriteria(User.class);
        return criteria.list();
    }

    public User getUserBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (User)list.get(0);
        }

        return null;
    }

    public User getUserBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (User)list.get(0);
        }
        return null;
    }

    public List<User> getUserListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(User.class);
        if(orderBy.equals("asc"))
        criteria.addOrder(Order.asc(column));
        else if (orderBy.equals("desc"))
        criteria.addOrder(Order.desc(column));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return list;
        }
        return null;
    }
}
