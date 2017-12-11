package cn.lcvc.dao.impl;

import cn.lcvc.POJO.ProductImg;
import cn.lcvc.dao.ProductImgDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImgDaoImpl implements ProductImgDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addProductImg(ProductImg productImg) {
        getSession().save(productImg);
    }

    public void deleteProductImg(ProductImg productImg) {
        getSession().delete(productImg);
    }

    public void updateProductImg(ProductImg productImg) {
        getSession().update(productImg);
    }

    public ProductImg getProductImg(Integer id) {
        Criteria criteria=getSession().createCriteria(ProductImg.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (ProductImg)list.get(0);
        }
        return null;
    }

    public List<ProductImg> getProductImgList() {
        Criteria criteria=getSession().createCriteria(ProductImg.class);
        return criteria.list();
    }

    public ProductImg getProductImgBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(ProductImg.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (ProductImg)list.get(0);
        }

        return null;
    }

    public ProductImg getProductImgBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(ProductImg.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (ProductImg)list.get(0);
        }
        return null;
    }

    public List<ProductImg> getProductImgListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(ProductImg.class);
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

    public List<ProductImg> getProductImgsBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(ProductImg.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        return (List<ProductImg>) list;

    }

    public List<ProductImg> getProductImgsBy_TowColumn_List(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(ProductImg.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        return (List<ProductImg>) list;
    }
}
