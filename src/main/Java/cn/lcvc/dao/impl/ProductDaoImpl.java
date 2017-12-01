package cn.lcvc.dao.impl;

import cn.lcvc.POJO.Product;
import cn.lcvc.dao.ProductDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDaoImpl implements ProductDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addProduct(Product product) {
        getSession().save(product);
    }

    public void deleteProduct(Product product) {
        getSession().delete(product);
    }

    public void updateProduct(Product product) {
        getSession().update(product);
    }

    public Product getProduct(Integer id) {
        Criteria criteria=getSession().createCriteria(Product.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Product)list.get(0);
        }
        return null;
    }

    public List<Product> getProductList() {
        Criteria criteria=getSession().createCriteria(Product.class);
        return criteria.list();
    }

    public Product getProductBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(Product.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Product)list.get(0);
        }

        return null;
    }

    public Product getProductBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(Product.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Product)list.get(0);
        }
        return null;
    }

    public List<Product> getProductListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(Product.class);
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
