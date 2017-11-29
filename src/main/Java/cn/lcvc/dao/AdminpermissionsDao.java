package cn.lcvc.dao;

import cn.lcvc.POJO.Adminpermissions;

import java.util.List;

public interface AdminpermissionsDao {

    public void addAdminpermissionsr(Adminpermissions adminpermissions);
    public void deleteAdminpermissionsr(Adminpermissions adminpermissions);
    public void updateAdminpermissionsr(Adminpermissions adminpermissions);
    public Adminpermissions getAdminpermissionsr(Integer id);
    public List<Adminpermissions> getAdminpermissionsrList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个Adminpermissionsr对象，null表示没查到
     */
    public Adminpermissions getAdminpermissionsrBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个Adminpermissionsr对象，null表示没查到
     */
    public Adminpermissions getAdminpermissionsrBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<Adminpermissionsr>集合，null表示没查到
     */
    public List<Adminpermissions> getAdminpermissionsrListOrderBy(String column, String orderBy);
}
