package cn.lcvc.service;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ProductImg;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.ProductDao;
import cn.lcvc.uitl.JsonResult;
import javafx.scene.chart.PieChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgService productImgService;
    /**
     * 商品发布
     * @param product 一个商品实体
     * @param sessionUser Session中的用户对象
     * @param urls 商品图片路径集合
     */
    public JsonResult issueProduct(Product product,User sessionUser,List<String> urls) {
        JsonResult jsonResult=new JsonResult();

        if(product.getProductName().length()<2||product.getProductName().length()>30)
        {
            jsonResult.setMessage("商品名称格式错误!应在2-30字符以内");
            return jsonResult;
        }
        if(product.getProductType()==null||product.getProductType().getId()==0)
        {
            jsonResult.setMessage("商品类型必须选择");
            return jsonResult;
        }
        if(product.getProductNumber()==null||product.getProductNumber()<=0)
        {
            jsonResult.setMessage("库存必须填写并且只能大于0");
            return jsonResult;
        }
        if(product.getProductPrice()<=0)
        {
            jsonResult.setMessage("商品价格必须填写并且只能是数字");
            return jsonResult;
        }
        if(product.getSchool()==null||product.getSchool().getId()==0)
        {
            jsonResult.setMessage("发布校园必须选择");
            return jsonResult;
        }
        if(product.getDegree()<=0)
        {
            jsonResult.setMessage("新旧程度必须选择");
            return jsonResult;
        }
        if(product.getGrounding()==null)
        {
            product.setGrounding(false);
        }
        if(product.getBuyTime()==null)
        {
            jsonResult.setMessage("入手时间必须填写");
            return jsonResult;
        }
        if(product.getExpire()==null)
        {
            jsonResult.setMessage("保修到期时间时间必须填写");
            return jsonResult;
        }
        product.setCriateTime(new Timestamp(System.currentTimeMillis()));
        product.setUser(sessionUser);
        product.setSeeNumber(0);
        productDao.addProduct(product);
        for (int i = 0; i < urls.size(); i++) {
            String s =  urls.get(i);
            productImgService.addProductImg(s,product.getId());
        }
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("发布成功");
        return jsonResult;
    }//完成

    /**
     * 删除一个商品
     * @param id 商品的Id
     */
   public JsonResult deleteProduct(Integer id) {
        JsonResult jsonResult=new JsonResult();
        if(id!=null&&id!=0) {
            Product product=productDao.getProduct(id);
            if(product!=null) {
                productDao.deleteProduct(product);
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("删除成功");
                return  jsonResult;
            }else{
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("数据库中不存在此商品");
                return jsonResult;
            }

        }

        jsonResult.setErrorCode("500");
        jsonResult.setMessage("删除失败");
        return jsonResult;

    }//完成

    /**
     * 获取所有用户List列表
     * @return JsonResult数据 获取到的用户列表存储在JsonResult.item中  key为"users"
     */
    public JsonResult getAllProduct() {
        JsonResult jsonResult=new JsonResult();
        List<Product> products=productDao.getProductList();
        Map<Object,Object> item=new HashMap<Object,Object>();
        item.put("products",products);
        jsonResult.setItem(item);

        jsonResult.setErrorCode("200");
        jsonResult.setMessage("获取成功");
        return jsonResult;

    }//完成
}
