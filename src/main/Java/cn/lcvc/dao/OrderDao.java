package cn.lcvc.dao;

import cn.lcvc.POJO.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    public void addOrder(Order order);
    public void deleteOrder(Order order);
    public void updateOrder(Order order);
    public Order getOrder(Integer id);
    public List<Order> getOrderList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个Order对象，null表示没查到
     */
    public Order getOrderBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个Order对象，null表示没查到
     */
    public Order getOrderBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<Order>集合，null表示没查到
     */
    public List<Order> getOrderListOrderBy(String column, String orderBy);

    /**
     *获取数据库中object对象。如map中存在数据则根据数据今天模糊查询
     *@Author @wuruibao
     *@Date 2017-12-5 11:46:33
     *@params
     *@return
     */
    List<Order> getOrder(Object  object, Map<String, Object> map);
}
