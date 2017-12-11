package cn.lcvc.dao.impl;

import cn.lcvc.POJO.ShoppingCartItem;
import cn.lcvc.dao.ShoppingCartItemDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCarItemDaoImpl implements ShoppingCartItemDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        getSession().save(shoppingCartItem);
    }

    public void deleteShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        getSession().delete(shoppingCartItem);
    }

    public void updateShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        ShoppingCartItem oldShoppingCartItem= (ShoppingCartItem) getSession().load(ShoppingCartItem.class,shoppingCartItem.getId());
        oldShoppingCartItem.setNumber(shoppingCartItem.getNumber());
        oldShoppingCartItem.setUser(shoppingCartItem.getUser());
        oldShoppingCartItem.setProduct(shoppingCartItem.getProduct());
        getSession().update(oldShoppingCartItem);
    }

    public ShoppingCartItem getShoppingCartItem(Integer id) {
        Criteria criteria=getSession().createCriteria(ShoppingCartItem.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (ShoppingCartItem)list.get(0);
        }
        return null;
    }

    public List<ShoppingCartItem> getShoppingCartItemList() {
        Criteria criteria=getSession().createCriteria(ShoppingCartItem.class);
        return criteria.list();
    }

    public ShoppingCartItem getShoppingCartItemBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(ShoppingCartItem.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (ShoppingCartItem)list.get(0);
        }

        return null;
    }

    public ShoppingCartItem getShoppingCartItemBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(ShoppingCartItem.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (ShoppingCartItem)list.get(0);
        }
        return null;
    }

    public List<ShoppingCartItem> getShoppingCartItemsBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(ShoppingCartItem.class);
        criteria.add(Restrictions.eq(column,value));
        return criteria.list();

    }

    public List<ShoppingCartItem> getShoppingCartItemsBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(ShoppingCartItem.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        return criteria.list();


    }


    public List<ShoppingCartItem> getShoppingCartItemListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(ShoppingCartItem.class);
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
