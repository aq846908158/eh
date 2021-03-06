package cn.lcvc.dao.impl;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ProductType;
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
        getSession().delete(getSession().load(Product.class,product.getId()));
        getSession().flush();
    }

    public void updateProduct(Product product) {
//        Product oldProduct = (Product) getSession().load(Product.class,product.getId());
//        oldProduct.setProductName(product.getProductName());
//        oldProduct.setProductNumber(product.getProductNumber());
//        oldProduct.setProductType(product.getProductType());
//        oldProduct.setProductPrice(product.getProductPrice());
//        oldProduct.setSchool(product.getSchool());
//        oldProduct.setProductIntroduce(product.getProductIntroduce());
//        oldProduct.setDegree(product.getDegree());
//        oldProduct.setGrounding(product.getGrounding());
//        oldProduct.setBuyTime(product.getBuyTime());
//        oldProduct.setExpire(product.getExpire());
//        oldProduct.setCriateTime(product.getCriateTime());
//        oldProduct.setUser(product.getUser());
//        oldProduct.setSeeNumber(product.getSeeNumber());
//        oldProduct.setState(product.getState());
        getSession().update(product);
        getSession().flush();
    }

    public Product getProduct(Integer id) {
        Criteria criteria=getSession().createCriteria(Product.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Product)list.get(0);
        }
        System.out.println("查询失败"+id);
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

    @Override
    public List<Product> getProductsBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(Product.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        return list;
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

    @Override
    public  List<Product>  getProductsBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(Product.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        return list;
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
        criteria.add(Restrictions.ne("state",true));//把虚拟删除标识符的商品筛除
        try{
            list=criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Product> getProductRandLimit() {
//        SELECT pro.*,productimg.imgUrl FROM (SELECT * FROM product WHERE state=0 AND grounding=1  ORDER BY id DESC  LIMIT 4,60) pro LEFT JOIN productimg ON pro.id=productimg.product AND productimg.id in (select min(productimg.id) from productimg group by productimg.product) 	 ORDER BY RAND() LIMIT 6
        List<Product> list=getSession().createSQLQuery("SELECT *FROM (SELECT * FROM product WHERE state=0 AND grounding=1  ORDER BY id DESC  LIMIT 4,60) pro ORDER BY RAND() LIMIT 6").addEntity(Product.class).list();

        return list;
    }

    @Override
    public List<Product> getFirstProduct() {
        //SELECT  product.*,productimg.imgUrl  FROM product   LEFT JOIN  productimg  ON  product.id=productimg.product  AND productimg.id in (select min(productimg.id) from productimg group by productimg.product) WHERE state=0 AND grounding=1 ORDER BY id DESC  LIMIT 4
//        List<Object> resultList=getSession().createSQLQuery("SELECT  product.*,productimg.imgUrl  FROM product   LEFT JOIN  productimg  ON  product.id=productimg.product  AND productimg.id in (select min(productimg.id) from productimg group by productimg.product) WHERE state=0 AND grounding=1 ORDER BY id DESC  LIMIT 4").list();

        List<Product> list= getSession().createSQLQuery("SELECT  product.*,productimg.imgUrl  FROM product   LEFT JOIN  productimg  ON  product.id=productimg.product  AND productimg.id in (select min(productimg.id) from productimg group by productimg.product) WHERE state=0 AND grounding=1 ORDER BY id DESC  LIMIT 4").addEntity(Product.class).list();
        return list;
    }

    @Override
    public List<Product> getNewLeave() {
//        SELECT pro.*,productimg.imgUrl FROM (SELECT * FROM product WHERE state=0 AND grounding=1 AND degree>=95 ORDER BY id DESC LIMIT 4,6) pro LEFT JOIN productimg ON pro.id=productimg.product AND productimg.id in (select min(productimg.id) from productimg group by productimg.product)  ORDER BY pro.id
        List<Product> list= getSession().createSQLQuery("SELECT * FROM (SELECT * FROM product WHERE state=0 AND grounding=1 AND degree>=95 ORDER BY id DESC LIMIT 4,6) p ORDER BY p.id").addEntity(Product.class).list();
        return list;
    }

    @Override
    public List<Product> getTopSeeNumber() {
//        SELECT product.*,productimg.imgUrl FROM  product LEFT JOIN  productimg  ON  product.id=productimg.product  AND productimg.id in (select min(productimg.id) from productimg group by productimg.product) WHERE state=0 AND grounding=1 ORDER BY seeNumber DESC  LIMIT 6
        List<Product> list= getSession().createSQLQuery("SELECT * FROM  product WHERE state=0 AND grounding=1 ORDER BY seeNumber DESC  LIMIT 6").addEntity(Product.class).list();
        return list;
    }
}
