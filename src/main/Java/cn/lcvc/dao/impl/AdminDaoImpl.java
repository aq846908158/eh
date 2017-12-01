package cn.lcvc.dao.impl;

import cn.lcvc.POJO.Admin;
import cn.lcvc.dao.AdminDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDaoImpl implements AdminDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addAdmin(Admin admin) {
        getSession().save(admin);
    }

    public void deleteAdmin(Admin admin) {
        getSession().delete(admin);
    }

    public void updateAdmin(Admin admin) {
        getSession().update(admin);
    }

    public Admin getAdmin(Integer id) {
        Criteria criteria=getSession().createCriteria(Admin.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Admin)list.get(0);
        }
        return null;
    }

    public List<Admin> getAdminList() {
        Criteria criteria=getSession().createCriteria(Admin.class);
        return criteria.list();
    }

    public Admin getAdminBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(Admin.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Admin)list.get(0);
        }

        return null;
    }

    public Admin getAdminBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(Admin.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Admin)list.get(0);
        }
        return null;
    }

    public List<Admin> getAdminListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(Admin.class);
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
