package cn.lcvc.service;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ProductType;
import cn.lcvc.POJO.School;
import cn.lcvc.POJO.User;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceTest extends BaseJunit{
    @Autowired
    private  ProductService productService;
    @Test
    public void issueProduct()
    {
        ProductType productType=new ProductType();
        productType.setId(3);
        School school=new School();
        school.setId(1);
        User user=new User();
        user.setId(8);
        Product product =new Product();
        product.setProductName("显卡");
        product.setProductType(productType);
        product.setProductPrice(888.0);
        product.setProductNumber(1);
        product.setSchool(school);
        product.setProductIntroduce("好好好");
        product.setDegree(9);
        product.setGrounding(true);
        product.setBuyTime(new Timestamp(System.currentTimeMillis()));
        product.setExpire(new Timestamp(System.currentTimeMillis()));
        List<String> urls=new ArrayList<String>();
        urls.add("/upload/2099-1231-32.png");
        urls.add("/upload/2099-1231-32.png");
        urls.add("/upload/2099-1231-32.png");

        JsonResult jsonResult=productService.issueProduct(product,user,urls);
        System.out.println(jsonResult.getMessage());
    }//通过

    @Test
    public void deleteProduct()
    {
        JsonResult jsonResult=productService.deleteProduct(1);
        System.out.println(jsonResult.getMessage());
    }

    @Test
    public void getAllUser() throws Exception
    {
        JsonResult jsonResult=productService.getAllProduct();
        List<Product> users= (List<Product>) jsonResult.getItem().get("products");
        System.out.println(users.size());
    }//通过
}
