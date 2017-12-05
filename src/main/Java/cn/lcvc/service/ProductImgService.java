package cn.lcvc.service;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ProductImg;
import cn.lcvc.dao.ProductImgDao;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImgService {
    @Autowired
    private ProductImgDao productImgDao;

    public JsonResult addProductImg(String url, Integer productId)
    {
        Product product=new Product();
        product.setId(productId);
        JsonResult jsonResult =new JsonResult();
        ProductImg productImg=new ProductImg();
        productImg.setImgUrl(url);
        productImg.setProduct(product);
        productImgDao.addProductImg(productImg);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("添加成功");
        return  jsonResult;
    }
}
