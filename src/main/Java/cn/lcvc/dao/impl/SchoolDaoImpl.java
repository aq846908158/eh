package cn.lcvc.dao.impl;

import cn.lcvc.POJO.School;
import cn.lcvc.dao.SchoolDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolDaoImpl implements SchoolDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addSchool(School school) {
        getSession().save(school);
    }

    public void deleteSchool(School school) {
        getSession().delete(school);
    }

    public void updateSchool(School school) {
        getSession().update(school);
    }

    public School getSchool(Integer id) {
        Criteria criteria=getSession().createCriteria(School.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (School)list.get(0);
        }
        return null;
    }

    public List<School> getSchoolList() {
        Criteria criteria=getSession().createCriteria(School.class);
        return criteria.list();
    }

    public School getSchoolBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(School.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (School)list.get(0);
        }

        return null;
    }

    public School getSchoolBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(School.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (School)list.get(0);
        }
        return null;
    }

    public List<School> getSchoolListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(School.class);
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

    public List<School> getSchoolBy_OneColumnLike(String column, String value) {
        Criteria criteria=getSession().createCriteria(School.class);
        criteria.add(Restrictions.like(column,"%"+value+"%"));
        List<School> list=criteria.list();
        if(list.size()!=0)
        {
            return list;
        }

        return null;
    }
}
