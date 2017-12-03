package cn.lcvc.dao;

import cn.lcvc.POJO.Admin;

import java.util.List;
import java.util.Map;

public interface AdminDao {

    public void addAdmin(Admin admin);

    public void deleteAdmin(Admin admin);

    public void updateAdmin(Admin admin);

    public Admin getAdmin(Integer id);

    public List<Admin> getAdminList();

    /**
     * @return 一个Admin对象，null表示没查到
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     */
    public Admin getAdminBy_OneColumn(String column, Object value);

    /**
     * @return 一个Admin对象，null表示没查到
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     */
    public Admin getAdminBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @return 一个有序List<Admin>集合，null表示没查到
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     */
    public List<Admin> getAdminListOrderBy(String column, String orderBy);

    /**
     * @param username 账户名，数据库唯一值
     * @author @wuruibao
     * date 2017年12月2日14:47:42
     * 获取管理员登录信息 ，获取对象中的salt和userpassword属性，用于登录验证；亦或可以判断username是否存在
     * @
     */
    public Admin getAdminByUserName(String username);

    /**
     * 获取数据库admin表中userName=admin.userName && id=admin.id的实体
     *
     * @param
     * @return
     * @Author @wuruibao
     * @Date 2017/12/2 23:00
     */
    public List<Admin> getAdminByUserNameInId(Admin admin);


    /**
     *获取数据库中object对象。如map中存在数据则根据数据今天模糊查询
     *@Author @wuruibao
     *@Date 2017/12/3 15:06
     *@params
     *@return
    */
    public List<Admin> queryAllAdminManage(Object object, Map<String, String> map);

}