package cn.lcvc.service;

import cn.lcvc.POJO.Favorites;
import cn.lcvc.dao.FavoritesDao;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *@Author @wuruibao
 *@Date 2017-12-13 14:18:00
*/
@Service
public class FavoritesService {
    @Autowired
    private FavoritesDao favoritesDao;


    /**
     * @wuruibao
     * 商品收藏功能
     * @param favorites
     * @return
     */
    public JsonResult registerOrCancelFavorites(Favorites favorites){
        JsonResult jsonResult = new JsonResult();

        if (favorites != null){
            if (favorites.getProduct() == null || favorites.getProduct().getId() == null){
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端:收藏失败,请刷新页面后重试.");
                return jsonResult;
            }
            if (favorites.getUser() == null || favorites.getUser().getId() == null){
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端:收藏失败,请刷新页面后重试.");
                return  jsonResult;
            }

        }
        //查询该用户是否已收藏该商品，如是，则删除该收藏，否则则添加收藏
        Favorites favorites_old = favoritesDao.getFavoritesBy_TowColumn("user",favorites.getUser(),"product",favorites.getProduct());
        if (favorites_old == null){
            favoritesDao.addFavorites(favorites);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("服务端:收藏成功.");
        }else {
            favoritesDao.deleteFavorites(favorites_old);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("服务端:取消收藏成功.");
        }


        return  jsonResult;

    }


}
