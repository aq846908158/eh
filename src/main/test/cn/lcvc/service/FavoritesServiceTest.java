package cn.lcvc.service;

import cn.lcvc.POJO.Favorites;
import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.User;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class FavoritesServiceTest extends  BaseJunit {

    @Autowired
    private FavoritesService favoritesService;

    @Test
    public void registerOrDeleteFavoritesTest(){
        Favorites favorites=new Favorites();
        Product product = new Product();
        User user = new User();
        product.setId(3);
        user.setId(4);
        favorites.setProduct(product);
        favorites.setUser(user);

        JsonResult jsonResult=favoritesService.registerOrDeleteFavorites(favorites);

        System.out.println(jsonResult.getMessage());

    }
}
