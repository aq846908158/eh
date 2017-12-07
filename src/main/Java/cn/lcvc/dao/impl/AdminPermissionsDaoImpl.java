package cn.lcvc.dao.impl;

import cn.lcvc.POJO.AdminPermissions;
import cn.lcvc.dao.AdminPermissionsDao;
import cn.lcvc.dao.AdminPermissionsDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminPermissionsDaoImpl implements AdminPermissionsDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addAdminPermissions(AdminPermissions adminPermissions) {
        getSession().save(adminPermissions);
    }

    public void deleteAdminPermissions(AdminPermissions adminPermissions) {
        getSession().delete(adminPermissions);
    }

    public void updateAdminPermissions(AdminPermissions adminPermissions) {
        getSession().update(adminPermissions);
    }

    public AdminPermissions getAdminPermissions(Integer id) {
        Criteria criteria=getSession().createCriteria(AdminPermissions.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (AdminPermissions)list.get(0);
        }
        return null;
    }

    public List<AdminPermissions> getAdminPermissionsList() {
        Criteria criteria=getSession().createCriteria(AdminPermissions.class);
        return criteria.list();
    }

    public AdminPermissions getAdminPermissionsBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(AdminPermissions.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (AdminPermissions)list.get(0);
        }

        return null;
    }

    public AdminPermissions getAdminPermissionsBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(AdminPermissions.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (AdminPermissions)list.get(0);
        }
        return null;
    }

    public List<AdminPermissions> getAdminPermissionsListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(AdminPermissions.class);
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

    public List<AdminPermissions> getAdminPermissionsList(Object object, Map<Object, Object> map) {
        List<AdminPermissions> adminPermissionsList=new ArrayList<AdminPermissions>();

        Criteria criteria = getSession().createCriteria((Class) object);

        for (Map.Entry  entry : map.entrySet()) {//遍历map

            criteria.add(Restrictions.eq((String) entry.getKey(),entry.getValue()));
        }

        try {
            adminPermissionsList = criteria.list();
        }catch (Exception e){
            e.getStackTrace();
        }

        return adminPermissionsList;
    }













}
