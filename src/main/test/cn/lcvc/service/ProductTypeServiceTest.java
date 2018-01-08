package cn.lcvc.service;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ProductType;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author @wuruibao
 * @Date 2017/12/921:31
 */
public class ProductTypeServiceTest extends BaseJunit {

    @Autowired
    private ProductTypeService productTypeService;


    /*产品类型管理*/
    @Test
    public  void getProductTypeTest(){
        ProductType productType =new ProductType();

//        productType.setProductTypeName("机");
//        productType.setProductTypeRank(3);
//        productType.setProductTypeCode("60373");
//        productType.setSuperType("759175");
        JsonResult jsonResult= productTypeService.getProductType(productType,"productTypeCode","desc");
        List<ProductType> list= (List<ProductType>) jsonResult.getItem().get("productTypeList");
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                System.out.print("产品名称  " + list.get(i).getProductTypeName());
                System.out.print("  产品等级  " + list.get(i).getProductTypeRank());
                System.out.print("  产品分类代码   " + list.get(i).getProductTypeCode());
                System.out.println("  产品父级类代码   " + list.get(i).getSuperType());

            }
        }
    }

    /* 添加产品类型*/
    @Test
    public void registerProductTypeTest(){
//        ProductType  productType =new ProductType();
//        productType.setProductTypeName("传呼机");
//        productType.setProductTypeRank(3);
//       productType.setSuperType("759175");
//        JsonResult jsonResult = productTypeService.registerProductType(productType);
//
//        System.out.println(jsonResult.getMessage());

    }

    /*删除产品类型*/
    @Test
    public void  deleteProductTypeTest(){
        JsonResult jsonResult =productTypeService.deleteProductType(5);

        System.out.println(jsonResult.getMessage());

    }
}
