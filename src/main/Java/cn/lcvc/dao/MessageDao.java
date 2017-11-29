package cn.lcvc.dao;

import cn.lcvc.POJO.Message;

import java.util.List;

public interface MessageDao {

    public void addMessager(Message message);
    public void deleteMessager(Message message);
    public void updateMessager(Message message);
    public Message getMessager(Integer id);
    public List<Message> getMessagerList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个Messager对象，null表示没查到
     */
    public Message getMessagerBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个Messager对象，null表示没查到
     */
    public Message getMessagerBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<Messager>集合，null表示没查到
     */
    public List<Message> getMessagerListOrderBy(String column, String orderBy);
}