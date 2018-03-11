package cn.lcvc.service;

import cn.lcvc.POJO.Order;
import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ShoppingCartItem;
import cn.lcvc.POJO.User;
import cn.lcvc.uitl.JsonResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartServiceTest extends BaseJunit {
    @Autowired
    private ShoppingCartItemService shoppingCartItemService;

    @Test
    public void addShoppingCartItem()
    {
        Product product=new Product();
        User user=new User();
        product.setId(2);
        user.setId(7);
//        JsonResult jsonResult=shoppingCartItemService.addShoppingCartItem(product,1,user);
//        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void deleteShoppingCartItem()
    {
//        JsonResult jsonResult=shoppingCartItemService.deleteShoppingCartItem(1);
//        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void updateShoppingCartItemProductNumber()
    {
//        JsonResult jsonResult=shoppingCartItemService.updateShoppingCartProductNumber(2,1);
//        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void getShoppingCartItemList()
    {
        User user=new User();
        user.setId(8);
        JsonResult jsonResult=shoppingCartItemService.getShoppingCartItemList(user);
        System.out.println(jsonResult.getList().size());
    }//通过

    @Test
    public void addShoppingCartItemBy_Session()
    {
        List<ShoppingCartItem> shoppingCartItems=new ArrayList<ShoppingCartItem>();
        JsonResult jsonResult= shoppingCartItemService.addShoppingCartItemBy_Session(shoppingCartItems,2,1);
        shoppingCartItems=jsonResult.getList();

        JsonResult jsonResult1=shoppingCartItemService.addShoppingCartItemBy_Session(shoppingCartItems,2,1);
        shoppingCartItems=jsonResult1.getList();

        JsonResult jsonResult2=shoppingCartItemService.addShoppingCartItemBy_Session(shoppingCartItems,2,1);
        shoppingCartItems=jsonResult1.getList();

        JsonResult jsonResult3=shoppingCartItemService.addShoppingCartItemBy_Session(shoppingCartItems,2,1);
        shoppingCartItems=jsonResult1.getList();

        for (int i = 0; i < shoppingCartItems.size(); i++) {
            ShoppingCartItem shoppingCartItem =  shoppingCartItems.get(i);
            System.out.println("商品:"+shoppingCartItem.getProduct().getProductName());
            System.out.println("数量"+shoppingCartItem.getNumber());
        }
        System.out.println("第一次"+jsonResult.getMessage());
        System.out.println("第二次"+jsonResult1.getMessage());
        System.out.println("第三次"+jsonResult2.getMessage());
        System.out.println("第四次"+jsonResult3.getMessage());

    }//通过

    @Test
    public void deleteShoppingCartItemBy_Session()
    {
        List<ShoppingCartItem> sessionShoppingCart=new ArrayList<ShoppingCartItem>();
        ShoppingCartItem shoppingCartItem1=new ShoppingCartItem();
        ShoppingCartItem shoppingCartItem2=new ShoppingCartItem();
        Product product1=new Product();
        Product product2=new Product();
        product1.setId(2);
        product2.setId(3);
        shoppingCartItem1.setNumber(3);
        shoppingCartItem2.setNumber(2);
        shoppingCartItem1.setProduct(product1);
        shoppingCartItem2.setProduct(product2);
        sessionShoppingCart.add(shoppingCartItem1);
        sessionShoppingCart.add(shoppingCartItem2);
        JsonResult jsonResult=shoppingCartItemService.deleteShoppingCartItemBy_Session(sessionShoppingCart,2);
        sessionShoppingCart=jsonResult.getList();
        for (int i = 0; i < sessionShoppingCart.size(); i++) {
            ShoppingCartItem shoppingCartItem =  sessionShoppingCart.get(i);
            System.out.println("商品:"+shoppingCartItem.getProduct().getId());
            System.out.println("数量"+shoppingCartItem.getNumber());
        }
        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void updateShoppingCartItemNumberBy_Session()
    {
        List<ShoppingCartItem> sessionShoppingCart=new ArrayList<ShoppingCartItem>();
        ShoppingCartItem shoppingCartItem1=new ShoppingCartItem();
        ShoppingCartItem shoppingCartItem2=new ShoppingCartItem();
        Product product1=new Product();
        Product product2=new Product();
        product1.setId(2);
        product2.setId(3);
        shoppingCartItem1.setNumber(3);
        shoppingCartItem2.setNumber(2);
        shoppingCartItem1.setProduct(product1);
        shoppingCartItem2.setProduct(product2);
        sessionShoppingCart.add(shoppingCartItem1);
        sessionShoppingCart.add(shoppingCartItem2);
        JsonResult jsonResult=shoppingCartItemService.updateShoppingCartItemNumberBy_Session(sessionShoppingCart,3,4);
        sessionShoppingCart=jsonResult.getList();
        for (int i = 0; i < sessionShoppingCart.size(); i++) {
            ShoppingCartItem shoppingCartItem =  sessionShoppingCart.get(i);
            System.out.println("商品:"+shoppingCartItem.getProduct().getId());
            System.out.println("数量"+shoppingCartItem.getNumber());
        }
        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public  void sessionShoppingCartToShoppingCart()
    {
        List<ShoppingCartItem> sessionShoppingCart=new ArrayList<ShoppingCartItem>();
        ShoppingCartItem shoppingCartItem1=new ShoppingCartItem();
        ShoppingCartItem shoppingCartItem2=new ShoppingCartItem();
        Product product1=new Product();
        Product product2=new Product();
        product1.setId(2);
        product2.setId(4);
        shoppingCartItem1.setNumber(1);
        shoppingCartItem2.setNumber(1);
        shoppingCartItem1.setProduct(product1);
        shoppingCartItem2.setProduct(product2);
        sessionShoppingCart.add(shoppingCartItem1);
        sessionShoppingCart.add(shoppingCartItem2);
        User user=new User();
        user.setId(8);
        JsonResult jsonResult=shoppingCartItemService.sessionShoppingCartToShoppingCart(sessionShoppingCart,user);
        System.out.println(jsonResult.getMessage());
    }

    @Test
    public void shoppingCartSettle()
    {
        User user=new User();
        user.setId(7);
        List<ShoppingCartItem> shoppingCartItems=shoppingCartItemService.getShoppingCartItemList(user).getList();
//        JsonResult jsonResult=shoppingCartItemService.shoppingCartSettle(shoppingCartItems,"我草草");
//        System.out.println(jsonResult.getMessage());
    }

    @Test
    public  void shoppingCartPay()
    {
        JsonResult jsonResult=shoppingCartItemService.shoppingCartSettleAndPay("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTExIiwiaXNzIjoiY29tLmVoIiwiZXhwIjoxNTEzNjgzMDY3LCJ1c2VySWQiOjcsImlhdCI6MTUxMzQyMzg2N30.5AVu3EtoqFXw2yMzJSljKW_HgeaSTnqVpXK5m_8jMfk");
        System.out.println(jsonResult.getMessage());
    }
}
