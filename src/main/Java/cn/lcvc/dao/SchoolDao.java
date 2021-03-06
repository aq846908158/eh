package cn.lcvc.dao;

import cn.lcvc.POJO.School;

import java.util.List;

public interface SchoolDao {

    public void addSchool(School school);
    public void deleteSchool(School school);
    public void updateSchool(School school);
    public School getSchool(Integer id);
    public List<School> getSchoolList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个School对象，null表示没查到
     */
     School getSchoolBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个School对象，null表示没查到
     */
     School getSchoolBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<School>集合，null表示没查到
     */
     List<School> getSchoolListOrderBy(String column, String orderBy);

    /**
     * @author wurubiao
     * @date 2017-12-12 09:43:02
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个list集合的School对象，null表示没查到
     */
    List<School> getSchoolBy_OneColumnLike(String column, String value);
}
