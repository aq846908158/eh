package cn.lcvc.dao.impl;

import cn.lcvc.POJO.Message;
import cn.lcvc.dao.MessageDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageDaoImpl implements MessageDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addMessage(Message message) {
        getSession().save(message);
    }

    public void deleteMessage(Message message) {
        getSession().delete(message);
    }

    public void updateMessage(Message message) {
        getSession().update(message);
    }

    public Message getMessage(Integer id) {
        Criteria criteria=getSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Message)list.get(0);
        }
        return null;
    }

    public List<Message> getMessageList() {
        Criteria criteria=getSession().createCriteria(Message.class);
        return criteria.list();
    }

    public Message getMessageBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Message)list.get(0);
        }

        return null;
    }

    public Message getMessageBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Message)list.get(0);
        }
        return null;
    }

    public List<Message> getMessageListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(Message.class);
        if(orderBy.equals("asc"))
        criteria.addOrder(Order.asc(column));
        else if (orderBy.equals("desc"))
        criteria.addOrder(Order.desc(column));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return list;
        }
        return null;
    }
}
