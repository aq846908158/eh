package cn.lcvc.dao;

import cn.lcvc.POJO.ProductType;

import java.util.List;
import java.util.Map;

public interface ProductTypeDao {

    public void addProductType(ProductType productType);
    public void deleteProductType(ProductType productType);
    public void updateProductType(ProductType productType);
    public ProductType getProductType(Integer id);
    public List<ProductType> getProductTypeList();

    /**
     * @author wuruibao
     * @date 2017-12-10 14:51:54
     * @param_object需要查询的对象
     * @param_map需要查询的字段的值
     * @return 一个list集合的ProductType对象，0表示没查到
     */
    List<ProductType> getProductTypeList(Object object, Map<Object,Object> map,String sort,String sortType);


    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个ProductType对象，null表示没查到
     */
    public ProductType getProductTypeBy_OneColumn(String column, Object value);

    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个List对象，size==0表示没查到
     */
    public List<ProductType> getProductTypeByList_OneColumn(String column, Object value);


    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个List对象，size==0表示没查到
     */
    public List<ProductType> getProductTypeByList_TwoColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个ProductType对象，null表示没查到
     */
    public ProductType getProductTypeBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<ProductType>集合，null表示没查到
     */
    public List<ProductType> getProductTypeListOrderBy(String column, String orderBy);
}
