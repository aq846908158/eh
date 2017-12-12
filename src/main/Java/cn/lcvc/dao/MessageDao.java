package cn.lcvc.dao;

import cn.lcvc.POJO.Message;

import java.util.List;

public interface MessageDao {

    public void addMessage(Message message);
    public void deleteMessage(Message message);
    public void updateMessage(Message message);
    public Message getMessage(Integer id);
    public List<Message> getMessageList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个Message对象，null表示没查到
     */
     Message getMessageBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个Message对象，null表示没查到
     */
     Message getMessageBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<Message>集合，null表示没查到
     */
     List<Message> getMessageListOrderBy(String column, String orderBy);
}
