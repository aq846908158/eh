package cn.lcvc.dao;

import cn.lcvc.POJO.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao {

    public void addProduct(Product product);
    public void deleteProduct(Product product);
    public void updateProduct(Product product);
    public Product getProduct(Integer id);
    public List<Product> getProductList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个Product对象，null表示没查到
     */
    public Product getProductBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个Product集合，null表示没查到
     */
    public List<Product> getProductsBy_OneColumn(String column, Object value);

    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个Product对象，null表示没查到
     */
    public Product getProductBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个Product集合，null表示没查到
     */
    public List<Product>  getProductsBy_TowColumn(String column1, Object value1, String column2, Object value2);


    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<Product>集合，null表示没查到
     */
    public List<Product> getProductListOrderBy(String column, String orderBy);

    /**
     * @author wuruibao
     * @date 2017-12-10 17:26:02
     * @param_object:查询的实体对象
     * @param_map：存放对应的数据库键值对条件
     * @return 一个有序List<Product>集合，null表示没查到
     */
    List<Product> getProductList(Object object, Map<Object, Object> map);
}
