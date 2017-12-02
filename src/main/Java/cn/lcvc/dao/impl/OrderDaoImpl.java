package cn.lcvc.dao.impl;

import cn.lcvc.POJO.Order;
import cn.lcvc.dao.OrderDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDaoImpl implements OrderDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addOrder(Order order) {
        getSession().save(order);
    }

    public void deleteOrder(Order order) {
        getSession().delete(order);
    }

    public void updateOrder(Order order) {
        getSession().update(order);
    }

    public Order getOrder(Integer id) {
        Criteria criteria=getSession().createCriteria(Order.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Order)list.get(0);
        }
        return null;
    }

    public List<Order> getOrderList() {
        Criteria criteria=getSession().createCriteria(Order.class);
        return criteria.list();
    }

    public Order getOrderBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(Order.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Order)list.get(0);
        }

        return null;
    }

    public Order getOrderBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(Order.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Order)list.get(0);
        }
        return null;
    }

    public List<Order> getOrderListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(Order.class);
        if(orderBy.equals("asc"))
        criteria.addOrder(org.hibernate.criterion.Order.asc(column));
        else if (orderBy.equals("desc"))
        criteria.addOrder(org.hibernate.criterion.Order.desc(column));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return list;
        }
        return null;
    }
}