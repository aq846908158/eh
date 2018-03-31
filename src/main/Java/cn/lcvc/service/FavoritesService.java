package cn.lcvc.service;

import cn.lcvc.POJO.Favorites;
import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.FavoritesDao;
import cn.lcvc.dao.ProductDao;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 *@Author @wuruibao
 *@Date 2017-12-13 14:18:00
*/
@Service
public class FavoritesService {
    @Autowired
    private FavoritesDao favoritesDao;
    @Autowired
    private ProductDao productDao;

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
                Product product=productDao.getProduct(favorites.getProduct().getId());
                if (product== null){
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端:收藏失败,请刷新页面后重试.");
                    return jsonResult;
                }
              favorites.setProduct(product);
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
            jsonResult.setMessage("已收藏");
        }else {
//            favoritesDao.deleteFavorites(favorites_old);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("您已经收藏过啦");
        }

        return  jsonResult;

    }


    public JsonResult getFavorites(User user) {
        JsonResult jsonResult = new JsonResult();

        List<Favorites> list=favoritesDao.getFavoritesListByUser(user);

        if (list.size() >0){
            jsonResult.setList(list);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("ok");
        }else {
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("ok");
        }

        return  jsonResult;

    }

    public JsonResult deleteFavorites(User user, Integer pid) {
        JsonResult jsonResult = new JsonResult();

        Favorites f=new Favorites();
        Product p=new Product();

        p.setId(pid);
        f.setProduct(p);
        f.setUser(user);
        f=favoritesDao.getFavoritesBy_TowColumn("user",user,"product",p);
        if (f != null){
            favoritesDao.deleteFavorites(f);
            f=favoritesDao.getFavoritesBy_TowColumn("user",user,"product",p);
            if (f == null){
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("已取消收藏");
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("取消收藏失败,请稍后重试");
            }
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("无法获取信息,请刷新后重试");
        }



        return  jsonResult;
    }
}
