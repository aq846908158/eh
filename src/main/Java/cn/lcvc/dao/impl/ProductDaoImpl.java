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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Product> getProductList(Object object, Map<Object, Object> map) {
        List<Product> list =new ArrayList<Product>();
        Criteria criteria=getSession().createCriteria((Class) object);

        for (Map.Entry  entry : map.entrySet()) {//遍历map
           if (entry.getKey().equals("productName")){
               criteria.add(Restrictions.like((String) entry.getKey(),"%"+entry.getValue()+"%"));
               continue;
           }
           //库存SQL操作
           if (entry.getKey().equals("lowProductNumber") || entry.getKey().equals("hiProductNumber")){
               if (map.get("lowProductNumber") != null && map.get("hiProductNumber") != null){// 如果两个变量都不为空的情况下，执行SQL between语句
                    criteria.add(Restrictions.between("productNumber",map.get("lowProductNumber"),map.get("hiProductNumber")));
               }else if (map.get("lowProductNumber") != null && map.get("hiProductNumber") == null){//最低
                    criteria.add(Restrictions.ge("productNumber",map.get("lowProductNumber")));
               }else if (map.get("lowProductNumber") == null && map.get("hiProductNumber") != null){
                   criteria.add(Restrictions.le("productNumber",map.get("hiProductNumber")));
               }
               continue;
           }

           //价格区间操作
           if (entry.getKey().equals("lowProductPrice") || entry.getKey().equals("hiProductPrice")){
               if (map.get("lowProductPrice") != null && map.get("hiProductPrice") != null){
                   criteria.add(Restrictions.between("productPrice",map.get("lowProductPrice"),map.get("hiProductPrice")));
               }else  if (map.get("lowProductPrice") != null && map.get("hiProductPrice") == null){
                   criteria.add(Restrictions.ge("productPrice",map.get("lowProductPrice")));
               }else  if (map.get("lowProductPrice") == null && map.get("hiProductPrice") != null) {
                   criteria.add(Restrictions.le("productPrice",map.get("hiProductPrice")));
               }
               continue;
           }
           //浏览量
            if (entry.getKey().equals("lowSeeNumber") || entry.getKey().equals("hiSeeNumber")){
                if (map.get("lowSeeNumber") != null && map.get("hiSeeNumber") != null){
                    criteria.add(Restrictions.between("seeNumber",map.get("lowSeeNumber"),map.get("hiSeeNumber")));
                }else  if (map.get("lowSeeNumber") != null && map.get("hiSeeNumber") == null){
                    criteria.add(Restrictions.ge("seeNumber",map.get("lowSeeNumber")));
                }else  if (map.get("lowSeeNumber") == null && map.get("hiSeeNumber") != null) {
                    criteria.add(Restrictions.le("seeNumber",map.get("hiSeeNumber")));
                }
                continue;
            }


            criteria.add(Restrictions.eq((String) entry.getKey(),entry.getValue()));
        }
        try{
            list=criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}
