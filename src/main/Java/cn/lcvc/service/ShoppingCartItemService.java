package cn.lcvc.service;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ShoppingCartItem;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.ProductDao;
import cn.lcvc.dao.ShoppingCartItemDao;
import cn.lcvc.dao.UserDao;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartItemService {
    @Autowired
    private ShoppingCartItemDao shoppingCartItemDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
//--------------------------------------------------------------------------------------------
    /**
     * 已登录用户       购物车商品添加
     * @param product 所需要添加进购物车的商品
     * @param number 需要购买的商品的数量
     * @param user Session中的user对象登录信息
     * @return 成功（JsonResult.message="添加成功"） 失败 （JsonResult.message="添加失败"）
     */
    public JsonResult addShoppingCartItem(Product product,Integer number,User user)
    {
        JsonResult jsonResult=new JsonResult();
        if(product!=null && product.getId()!=0 && number!=0 && user!=null && user.getId()!=0) {
            ShoppingCartItem shoppingCartItem = shoppingCartItemDao.getShoppingCartItemBy_TowColumn("product",product,"user",user);
            if(shoppingCartItem==null) {
                //判断商品库存是否充足
                if (!productNumberIs(product.getId(),number))
                {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("商品库存不足");
                    return  jsonResult;
                }
                shoppingCartItem=new ShoppingCartItem();
                shoppingCartItem.setProduct(product);
                shoppingCartItem.setNumber(number);
                shoppingCartItem.setUser(user);
                shoppingCartItemDao.addShoppingCartItem(shoppingCartItem);
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("添加成功");
                return jsonResult;
            }
            else
            {
                //判断商品库存是否充足
                if (!productNumberIs(product.getId(),number+shoppingCartItem.getNumber()))
                {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("商品库存不足");
                    return  jsonResult;
                }
                shoppingCartItem.setNumber(number+shoppingCartItem.getNumber());
                shoppingCartItemDao.updateShoppingCartItem(shoppingCartItem);
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("添加成功");
                return  jsonResult;
            }
        }
        jsonResult.setErrorCode("500");
        jsonResult.setMessage("添加失败");
        return  jsonResult;
    }
//--------------------------------------------------------------------------------------------
    /**
     * 已登录用户       购物车项移除方法
     * @param id 购物车项id
     * @return 成功（JsonResult.message="删除成功"） 失败 （JsonResult.message="删除失败"）
     */
    public JsonResult deleteShoppingCartItem(Integer id)
    {
        JsonResult jsonResult =new JsonResult();

        ShoppingCartItem shoppingCartItem=shoppingCartItemDao.getShoppingCartItem(id);
        if(shoppingCartItem!=null)
        {
            shoppingCartItemDao.deleteShoppingCartItem(shoppingCartItem);
            jsonResult.setMessage("删除成功");
            jsonResult.setErrorCode("200");
            return  jsonResult;
        }
        jsonResult.setMessage("删除失败");
        jsonResult.setErrorCode("500");
        return  jsonResult;
    }
//--------------------------------------------------------------------------------------------
    /**
     * 已登录用户       修改购物车项商品数量的修改
     * @param id 购物车项Id
     * @param number 修改的数量
     * @return 成功（JsonResult.message="修改成功"） 失败 （JsonResult.message="修改失败"）
     */
    public JsonResult updateShoppingCartProductNumber(Integer id,Integer number)
    {
        JsonResult jsonResult=new JsonResult();
        ShoppingCartItem shoppingCartItem=shoppingCartItemDao.getShoppingCartItem(id);
        if (shoppingCartItem!=null)
        {
            //判断商品库存是否充足
            if (!productNumberIs(id,number))
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("商品库存不足");
                return  jsonResult;
            }
            shoppingCartItem.setNumber(number);
            shoppingCartItemDao.updateShoppingCartItem(shoppingCartItem);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("修改成功");
            return jsonResult;
        }
        jsonResult.setErrorCode("500");
        jsonResult.setMessage("修改失败");
        return jsonResult;
    }
//--------------------------------------------------------------------------------------------
    /**
     * 已登录用户根据Session中的登录信息获取购物车列表
     * @param user Session中的登录信息
     * @return 结果集存放在JsonResult.list中
     */
    public JsonResult getShoppingCartItemList(User user)
    {
        JsonResult jsonResult=new JsonResult();
        jsonResult.setList(shoppingCartItemDao.getShoppingCartItemsBy_OneColumn("user",user));
        jsonResult.setMessage("获取成功");
        jsonResult.setErrorCode("200");
        return  jsonResult;
    }
//--------------------------------------------------------------------------------------------
    /**
     * 未登录用户添加商品进入购物车
     * @param sessionShoppingCart 控制层获取到Session中的购物车信息
     * @param productId 添加的产品id
     * @param number 添加的数量
     * @return (JsonResult.list)中会存放新的购物车集合，请取出后在控制层更新到Session中
     */
    public JsonResult addShoppingCartItemBy_Session(List<ShoppingCartItem> sessionShoppingCart,Integer productId,Integer number)
    {
        JsonResult jsonResult=new JsonResult();
        Product product=productDao.getProduct(productId);
        //判断商品是否存在
        if(product==null)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("添加失败");
            jsonResult.setList(sessionShoppingCart);
            return jsonResult;
        }
        //判断库存是否足够
        if (!productNumberIs(productId,number))
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("商品库存不足");
            jsonResult.setList(sessionShoppingCart);
            return  jsonResult;
        }
        //判断购物车中是否已经存在这个商品
        //存在
        for (int i = 0; i < sessionShoppingCart.size(); i++) {
            ShoppingCartItem shoppingCartItem =  sessionShoppingCart.get(i);
            if(shoppingCartItem.getProduct().equals(product))
            {
                //如果已经存在  判断已有记录的数量加上需要新增的数量  库存是否能够满足
                //如果库存不满足
                if (!productNumberIs(productId,number+shoppingCartItem.getNumber()))
                {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("商品库存不足");
                    jsonResult.setList(sessionShoppingCart);
                    return  jsonResult;
                }
                //如果库存满足 在原有记录基础上增加数量
                else
                {
                    shoppingCartItem.setNumber(number+shoppingCartItem.getNumber());
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("添加成功");
                    jsonResult.setList(sessionShoppingCart);
                    return jsonResult;
                }
            }
        }
        //购物车不存在时，新增一条购物车记录
        ShoppingCartItem shoppingCartItem=new ShoppingCartItem();
        shoppingCartItem.setNumber(number);
        shoppingCartItem.setProduct(product);
        sessionShoppingCart.add(shoppingCartItem);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("添加成功");
        jsonResult.setList(sessionShoppingCart);
        return jsonResult;


    }
//--------------------------------------------------------------------------------------------
    /**
     * 未登录用户的购物车项删除
     * @param sessionShoppingCart 控制层获取到Session中的购物车信息
     * @param productId 删除的购物车项中商品的id
     * @return (JsonResult.list)中会存放新的购物车集合，请取出后在控制层更新到Session中
     */
    public JsonResult deleteShoppingCartItemBy_Session(List<ShoppingCartItem> sessionShoppingCart,Integer productId)
    {
        JsonResult jsonResult=new JsonResult();
        ShoppingCartItem shoppingCartItem=new ShoppingCartItem();
        Product product=new Product();
        product.setId(productId);
        shoppingCartItem.setProduct(product);
        int index=sessionShoppingCart.indexOf(shoppingCartItem);
       if (index==-1) {
           jsonResult.setMessage("删除失败");
           jsonResult.setErrorCode("500");
           jsonResult.setList(sessionShoppingCart);
           return jsonResult;
       }
       sessionShoppingCart.remove(index);
        jsonResult.setMessage("删除成功");
        jsonResult.setErrorCode("200");
        jsonResult.setList(sessionShoppingCart);
        return  jsonResult;
    }
//--------------------------------------------------------------------------------------------
    /**
     * 未登录用户购物车商品数量修改
     * @param sessionShoppingCart 控制层获取到Session中的购物车信息
     * @param productId 删除的购物车项中商品的id
     * @param number 修改的数量
     * @return (JsonResult.list)中会存放新的购物车集合，请取出后在控制层更新到Session中
     */
    public JsonResult updateShoppingCartItemNumberBy_Session(List<ShoppingCartItem> sessionShoppingCart,Integer productId,Integer number)
    {
        JsonResult jsonResult=new JsonResult();
        ShoppingCartItem shoppingCartItem=new ShoppingCartItem();
        Product product=new Product();
        product.setId(productId);
        shoppingCartItem.setProduct(product);
        int index=sessionShoppingCart.indexOf(shoppingCartItem);
        if(index!=-1)
        {
            if (!productNumberIs(productId,number))
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("商品库存不足 ");
                jsonResult.setList(sessionShoppingCart);
                return  jsonResult;
            }
            sessionShoppingCart.get(index).setNumber(number);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("修改成功");
            jsonResult.setList(sessionShoppingCart);
            return  jsonResult;
        }
        jsonResult.setErrorCode("500");
        jsonResult.setMessage("修改失败");
        jsonResult.setList(sessionShoppingCart);
        return  jsonResult;
    }

//--------------------------------------------------------------------------------------------
    /**
     * 未登录用户登陆后购物车同步到账号
     * @param sessionShoppingCart 未登录时的购物车信息
     * @param user 登录的user
     * @return JsonResult信息
     */
    public JsonResult sessionShoppingCartToShoppingCart(List<ShoppingCartItem> sessionShoppingCart,User user)
    {
        JsonResult jsonResult=new JsonResult();
        if(user!=null&&user.getId()!=0) {
            for (int i = 0; i < sessionShoppingCart.size(); i++) {
                ShoppingCartItem shoppingCartItem = sessionShoppingCart.get(i);
                addShoppingCartItem(shoppingCartItem.getProduct(),shoppingCartItem.getNumber(),user);
            }
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("转换成功");
            return jsonResult;
        }
        jsonResult.setErrorCode("500");
        jsonResult.setMessage("转换失败");
        return jsonResult;
    }

    public JsonResult shoppingCartSettle()
    {
        JsonResult jsonResult=new JsonResult();

        return  jsonResult;
    }







    /**
     * （封装方法）判断商品数量是否足够
     * @param id 商品Id
     * @param number 所需商品数量
     * @return 足够返回true 不足返回 false
     */
    public boolean productNumberIs(Integer id,Integer number)
    {
        Product product=productDao.getProduct(id);
        if(product!=null)
        {
            if(product.getProductNumber()>=number)
            {
                return true;
            }
            return  false;
        }
        return  false;
    }
}