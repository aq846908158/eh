package cn.lcvc.dao.impl;

import cn.lcvc.POJO.Order;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.OrderDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        getSession().delete(getSession().load(Order.class,order.getId()));
        getSession().flush();

    }

    public void updateOrder(Order order) {
        Order oldOrder= (Order) getSession().load(Order.class,order.getId());
        oldOrder.setOrderCode(order.getOrderCode());
        oldOrder.setNumber(order.getNumber());
        oldOrder.setProduct(order.getProduct());
        oldOrder.setBuyUser(oldOrder.getBuyUser());
        oldOrder.setOrderPrice(order.getOrderPrice());
        oldOrder.setOrderState(order.getOrderState());
        oldOrder.setCreateTime(order.getCreateTime());
        oldOrder.setMessage(order.getMessage());
        getSession().update(oldOrder);
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

    public List<Order> getOrder(Object object, Map<String, Object> map,String column,String orderBy) {
        List<Order> list =new ArrayList<Order>();
        Criteria criteria=getSession().createCriteria((Class) object);

        for (Map.Entry  entry : map.entrySet()) {//遍历map
            if (entry.getKey().equals("lowOrderPrice") || entry.getKey().equals("heightOrderPrice") ) {
                criteria.add(Restrictions.between("orderPrice",map.get("lowOrderPrice"),map.get("heightOrderPrice")));//价格区间搜索
                continue;
            }
            if (entry.getKey().equals("orderCode")){
                criteria.add(Restrictions.like((String) entry.getKey(), "%"+entry.getValue()+"%")); //订单号模糊查询
                continue;
            }

                criteria.add(Restrictions.eq((String) entry.getKey(),entry.getValue()));


        }
        if(orderBy.equals("asc"))
            criteria.addOrder(org.hibernate.criterion.Order.asc(column));
        else if (orderBy.equals("desc"))
            criteria.addOrder(org.hibernate.criterion.Order.desc(column));

        try{
            list=criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Order> getOrderByUser(User user, Map<String, Object> map) {
        List<Order> list =new ArrayList<Order>();
        Criteria criteria=getSession().createCriteria(Order.class);
        for (Map.Entry  entry : map.entrySet()) {//遍历map
            criteria.add(Restrictions.like((String) entry.getKey(),"%"+entry.getValue()+"%"));
        }
        criteria.add(Restrictions.eq("buyUser",user));
        criteria.addOrder(org.hibernate.criterion.Order.desc("id"));
        return criteria.list();
    }

    @Override
    public List<Order> getorderInId(Integer[] oid) {
        String hql = "from Order  where id in (:oid) and orderState='0'";
        Query query=getSession().createQuery(hql);
        query.setParameterList("oid", oid);

        List<Order> orderList=query.list();
       if (orderList.size() > 0){
           return  orderList;
       }else {
           return null;
       }

    }
}
