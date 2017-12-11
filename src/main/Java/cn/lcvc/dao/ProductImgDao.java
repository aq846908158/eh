package cn.lcvc.dao;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ProductImg;

import java.util.List;

public interface ProductImgDao {

    public void addProductImg(ProductImg productImg);
    public void deleteProductImg(ProductImg productImg);
    void deleteProductImgByProduct(Product product);
    public void updateProductImg(ProductImg productImg);
    public ProductImg getProductImg(Integer id);
    public List<ProductImg> getProductImgList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个ProductImg对象，null表示没查到
     */
    public ProductImg getProductImgBy_OneColumn(String column, Object value);

    /**
     * @author wurubiao
     * @date 2017-12-11 19:40:13
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个List集合的ProductImg对象，null表示没查到
     */
     List<ProductImg> getProductImgList(String column, Object value);

    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个ProductImg对象，null表示没查到
     */
    public ProductImg getProductImgBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<ProductImg>集合，null表示没查到
     */
    public List<ProductImg> getProductImgListOrderBy(String column, String orderBy);

    /**
     * @author huanghaibin
     * @date 2017-12-8 09:06:16
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个ProductImg集合，null表示没查到
     */
    public List<ProductImg> getProductImgsBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-12-8 09:06:13
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个ProductImg集合，null表示没查到
     */
    public List<ProductImg> getProductImgsBy_TowColumn_List(String column1, Object value1, String column2, Object value2);
}
