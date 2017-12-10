package cn.lcvc.service;

import cn.lcvc.POJO.ProductType;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author @wuruibao
 * @Date 2017/12/921:31
 */
public class ProductTypeServiceTest extends BaseJunit {

    @Autowired
    private ProductTypeService productTypeService;

    /* 添加产品类型*/
    @Test
    public void registerProductTypeTest(){
        ProductType  productType =new ProductType();
        productType.setProductTypeName("座机");
        productType.setProductTypeRank(3);
        productType.setSuperType("9334");
        JsonResult jsonResult = productTypeService.registerProductType(productType);

        System.out.println(jsonResult.getMessage());

    }

    /*删除产品类型*/
    @Test
    public void  deleteProductTypeTest(){
        JsonResult jsonResult =productTypeService.deleteProductType(1);

        System.out.println(jsonResult.getMessage());

    }
}
