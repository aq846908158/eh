package cn.lcvc.dao.impl;

import cn.lcvc.POJO.Favorites;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.FavoritesDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritesDaoImpl implements FavoritesDao {

    /**自动注入*/
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return  sessionFactory.getCurrentSession();
    }

    public void addFavorites(Favorites favorites) {
        getSession().save(favorites);
    }

    public void deleteFavorites(Favorites favorites) {
        getSession().delete(favorites);
        getSession().flush();

    }

    public void updateFavorites(Favorites favorites) {
        getSession().update(favorites);
    }

    public Favorites getFavorites(Integer id) {
        Criteria criteria=getSession().createCriteria(Favorites.class);
        criteria.add(Restrictions.eq("id",id));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Favorites)list.get(0);
        }
        return null;
    }

    public List<Favorites> getFavoritesList() {
        Criteria criteria=getSession().createCriteria(Favorites.class);
        return criteria.list();
    }

    public Favorites getFavoritesBy_OneColumn(String column, Object value) {
        Criteria criteria=getSession().createCriteria(Favorites.class);
        criteria.add(Restrictions.eq(column,value));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Favorites)list.get(0);
        }

        return null;
    }

    public Favorites getFavoritesBy_TowColumn(String column1, Object value1, String column2, Object value2) {
        Criteria criteria=getSession().createCriteria(Favorites.class);
        criteria.add(Restrictions.eq(column1,value1));
        criteria.add(Restrictions.eq(column2,value2));
        List list=criteria.list();
        if(list.size()!=0)
        {
            return (Favorites)list.get(0);
        }
        return null;
    }

    public List<Favorites> getFavoritesListOrderBy(String column, String orderBy) {
        Criteria criteria=getSession().createCriteria(Favorites.class);
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

    @Override
    public List<Favorites> getFavoritesListByUser(User user) {
        Criteria criteria=getSession().createCriteria(Favorites.class);
        criteria.add(Restrictions.eq("user",user));
        return  criteria.list();
    }
}
