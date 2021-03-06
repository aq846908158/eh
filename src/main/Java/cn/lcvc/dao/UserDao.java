package cn.lcvc.dao;

import cn.lcvc.POJO.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface UserDao {

    public void addUser(User user);
    public void deleteUser(User user);
    public void updateUser(User user);
    public User getUser(Integer id);
    public List<User> getUserList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个User对象，null表示没查到
     */
    public User getUserBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个User对象，null表示没查到
     */
    public User getUserBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<User>集合，null表示没查到
     */
    public List<User> getUserListOrderBy(String column, String orderBy);

    /**
     *获取数据库中object对象。如map中存在数据则根据数据进行查询
     *@Author @wuruibao
     *@Date 2017-12-5 19:42:55
     *@params object 对象 map:map集合
     *@return 返回查询List
     */
    List<User> getUser(Object object, Map<String, Object> map);
}
