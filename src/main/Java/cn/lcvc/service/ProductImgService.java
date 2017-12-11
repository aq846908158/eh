package cn.lcvc.service;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ProductImg;
import cn.lcvc.dao.ProductImgDao;
import cn.lcvc.uitl.JsonResult;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.ObjectIdMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @param id 一个商品Id
     * 根据ProductId 获取一个商品图片List集合
     * @return JsonResult数据，查询到的结果集会存放在(JsonResult.list)中
     */
    public JsonResult getProductImgBy_PId(Integer id)
    {
        JsonResult jsonResult=new JsonResult();
        Product product=new Product();
        product.setId(id);
        List<ProductImg> productImgs= (List<ProductImg>) productImgDao.getProductImgsBy_OneColumn("product",product);
        jsonResult.setMessage("查询成功");
        jsonResult.setList(productImgs);
        return jsonResult;
    }

    /**
     * @param id 一个图片Id
     * 根据ProductId 获取一个商品图片List集合
     * @return JsonResult数据，查询到的结果集会存放在(JsonResult.list)中
     */
    public JsonResult deleteProductImg(Integer id)
    {
        JsonResult jsonResult = new JsonResult();
        if(id!=null&&id!=0) {

            ProductImg productImg=productImgDao.getProductImg(id);

            if(productImg!=null&&productImg.getId()!=0){
                productImgDao.deleteProductImg(productImg);
            }
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("删除成功");
            return  jsonResult;
        }
        return jsonResult;
    }
}
