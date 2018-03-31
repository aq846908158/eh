package cn.lcvc.dao;

import cn.lcvc.POJO.Favorites;
import cn.lcvc.POJO.User;

import java.util.List;
import java.util.Objects;

public interface FavoritesDao {

     void addFavorites(Favorites favorites);
     void deleteFavorites(Favorites favorites);
     void updateFavorites(Favorites favorites);
     Favorites getFavorites(Integer id);
     List<Favorites> getFavoritesList();
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:35:15
     * @param_column需要查询的字段
     * @param_value需要查询的字段的值
     * @return 一个Favorites对象，null表示没查到
     */
     Favorites getFavoritesBy_OneColumn(String column, Object value);
    /**
     * @author huanghaibin
     * @date 2017-11-29 16:59:53
     * @param_ column1需要查询的第一个字段
     * @param_ value1需要查询的第一个字段的值
     * @param_ column1需要查询的第二个字段
     * @param_ value1需要查询的第二个字段的值
     * @return 一个Favorites对象，null表示没查到
     */
     Favorites getFavoritesBy_TowColumn(String column1, Object value1, String column2, Object value2);

    /**
     * @author huanghaibin
     * @date 2017-11-29 17:21:59
     * @param_column排序所依据的字段
     * @param_orderBy排序规则 如"asc","desc"
     * @return 一个有序List<Favorites>集合，null表示没查到
     */
     List<Favorites> getFavoritesListOrderBy(String column, String orderBy);

    List<Favorites> getFavoritesListByUser(User user);
}
