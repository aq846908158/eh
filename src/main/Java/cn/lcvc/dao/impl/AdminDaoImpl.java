package cn.lcvc.dao.impl;

import cn.lcvc.POJO.Admin;
import cn.lcvc.dao.AdminDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
        getSession().delete(getSession().load(Admin.class,admin.getId()));
        getSession().flush();
    }

    public void updateAdmin(Admin admin) {
        getSession().update(admin);
        getSession().flush();
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

    public Admin getAdminByUserName(String username) {
        Criteria criteria=getSession().createCriteria(Admin.class);
        criteria.add(Restrictions.eq("userName",username));
        List list = criteria.list();
        if(list.size() != 0){
            return (Admin) list.get(0);
        }
        return null;
    }

    public List<Admin> getAdminByUserNameInId(Admin admin) {
        String hql = "from Admin  where userName=? and id!=?";
        Query query=getSession().createQuery(hql);
        query.setString(0, admin.getUserName());
        query.setInteger(1, admin.getId());
        List<Admin> list = query.list();
        return  list;
    }

    public List<Admin> queryAllAdminManage(Object object, Map<String, Object> map,String sort,String sortType) {
        List<Admin> list = new ArrayList<Admin>();
        Criteria criteria=getSession().createCriteria((Class) object);
        if (map.get("seltype") != null && map.get("seltype").equals("like")  ){
            for (Map.Entry  entry : map.entrySet()) {
                if (entry.getKey().equals("seltype")) continue;
                criteria.add(Restrictions.like((String) entry.getKey(), "%"+entry.getValue()+"%"));
            }
        }else if (map.get("seltype") != null && map.get("seltype").equals("eq")){
            for (Map.Entry  entry : map.entrySet()) {
                if (entry.getKey().equals("seltype")) continue;
                 criteria.add(Restrictions.eq((String) entry.getKey(), entry.getValue()));
            }
        }else {
            for (Map.Entry  entry : map.entrySet()) {
                criteria.add(Restrictions.like((String) entry.getKey(), "%"+entry.getValue()+"%"));
            }
        }
             if(sortType.equals("asc"))
            criteria.addOrder(Order.asc(sort));
             else if (sortType.equals("desc"))
            criteria.addOrder(Order.desc(sort));
        try {
            list = criteria.list();

        } catch (Exception e) {

        }finally{

        }
        return list;
    }

    @Override
    public Admin getAdminInfo(String sql) {
        Admin admin = new Admin();
        Query query=getSession().createQuery(sql);
//默认查询出来的list里存放的是一个Object数组，还需要转换成对应的javaBean。
        List<Object[]> admins = query.list();
        if (admins.size() != 0){
            for(Object[] adminObj : admins){
                admin.setId((Integer) adminObj[0]);
               admin.setUserName((String) adminObj[1]);
                admin.setTrueName((String) adminObj[2]);
                admin.setPhone((String) adminObj[3]);
                admin.setEmail((String) adminObj[4]);
            }
            return  admin;
        }
        return null;
    }


}
