package cn.lcvc.dao.impl;

import cn.lcvc.POJO.AdminPermissions;
import cn.lcvc.POJO.ProductType;
import cn.lcvc.dao.ProductTypeDao;
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
        getSession().delete(getSession().load(ProductType.class,productType.getId()));
        getSession().flush();
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

    public List<ProductType> getProductTypeList(Object object, Map<Object, Object> map,String sort,String sortType) {
        List<ProductType> productTypeArrayList=new ArrayList<ProductType>();

        Criteria criteria = getSession().createCriteria((Class) object);

        for (Map.Entry  entry : map.entrySet()) {//遍历map
            if (entry.getKey().equals("productTypeName")){ //产品分类名称执行模糊查询
                criteria.add(Restrictions.like((String) entry.getKey(),"%"+entry.getValue()+"%"));
                continue;
            }
            criteria.add(Restrictions.eq((String) entry.getKey(),entry.getValue()));
        }
        if(sortType.equals("asc"))
            criteria.addOrder(Order.asc(sort));
        else if (sortType.equals("desc"))
            criteria.addOrder(Order.desc(sort));
        try {
            productTypeArrayList = criteria.list();
        }catch (Exception e){
            e.getStackTrace();
        }
        return productTypeArrayList;
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

    public List<ProductType> getProductTypeByList_OneColumn(String column, Object value) {
        List<ProductType> list = new ArrayList<ProductType>();
        Criteria criteria=getSession().createCriteria(ProductType.class);
        criteria.add(Restrictions.eq(column,value));
        list=criteria.list();
        if(list.size()!=0)
        {
            return list;
        }

        return list;
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
