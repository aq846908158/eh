package cn.lcvc.dao.impl;

import cn.lcvc.POJO.ProductType;
import cn.lcvc.dao.ProductTypeDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeDaoImpl implements ProductTypeDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addProductType(ProductType productType) {
        getSession().save(productType);
    }

    public void deleteProductType(ProductType productType) {
        getSession().delete(productType);
    }

    public void updateProductType(ProductType productType) {
        getSession().update(productType);
    }

    public ProductType getProductType(Integer id) {
        Criteria criteria=getSession().createCriteria(ProductType.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (ProductType)list.get(0);
        }
        return null;
    }

    public List<ProductType> getProductTypeList() {
        Criteria criteria=getSession().createCriteria(ProductType.class);
        return criteria.list();
    }

    public ProductType getProductTypeBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(ProductType.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (ProductType)list.get(0);
        }

        return null;
    }

    public ProductType getProductTypeBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(ProductType.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (ProductType)list.get(0);
        }
        return null;
    }

    public List<ProductType> getProductTypeListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(ProductType.class);
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
