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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<User> getUser(Object object, Map<String, Object> map) {
        List<User> users = new ArrayList<User>();
        Criteria criteria = getSession().createCriteria((Class) object);
        boolean sellNumberCount=false,forSaleNumberCount=false;//判断是否首次执行 销售量 、在售量 的between操作 ；首次执行过后把属性变为true，避免二次查询
        for (Map.Entry  entry : map.entrySet()) {//遍历map

            if (entry.getKey().equals("lowSellNumber") || entry.getKey().equals("hiSellNumber")){
                if (sellNumberCount == false){
                    criteria.add(Restrictions.between("sellNumber",map.get("lowSellNumber"),map.get("hiSellNumber")));//销售成功区间搜索
                    sellNumberCount=true;
                }
                continue;
            }
            if (entry.getKey().equals("lowForSaleNumber") || entry.getKey().equals("hiForSaleNumber")){
                if (!forSaleNumberCount){
                    criteria.add(Restrictions.between("forSaleNumber",map.get("lowForSaleNumber"),map.get("hiForSaleNumber")));//在售数量区间搜索
                    forSaleNumberCount=true;
                }
                continue;
            }

            if (entry.getKey().equals("banLogin")|| entry.getKey().equals("banSell")){
                criteria.add(Restrictions.eq((String) entry.getKey(),entry.getValue()));
                continue;
            }

            criteria.add(Restrictions.like((String) entry.getKey(),"%"+entry.getValue()+"%"));

        }

        try{
            users=criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }

        return users;
    }











}
