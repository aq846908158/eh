package cn.lcvc.dao;

import cn.lcvc.POJO.AdminPermissions;

import java.util.List;

public interface AdminPermissionsDao {

    public void addAdminPermissions(AdminPermissions adminPermissions);
    public void deleteAdminPermissions(AdminPermissions adminPermissions);
    public void updateAdminPermissions(AdminPermissions adminPermissions);
    public AdminPermissions getAdminPermissions(Integer id);
    public List<AdminPermissions> getAdminPermissionsList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个AdminPermissions对象，null表示没查到
     */
    public AdminPermissions getAdminPermissionsBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个AdminPermissions对象，null表示没查到
     */
    public AdminPermissions getAdminPermissionsBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<AdminPermissions>集合，null表示没查到
     */
    public List<AdminPermissions> getAdminPermissionsListOrderBy(String column, String orderBy);
}
