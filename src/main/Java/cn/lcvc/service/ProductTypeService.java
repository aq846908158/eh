package cn.lcvc.service;

import cn.lcvc.POJO.ProductType;
import cn.lcvc.dao.AdminPermissionsDao;
import cn.lcvc.dao.ProductTypeDao;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.tree.Tree;

/**
 * @Author @wuruibao
 * @Date 2017/12/920:11
 */

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private AdminPermissionsDao adminPermissionsDao;

    public JsonResult registerProductType(ProductType productType){
        JsonResult jsonResult = new JsonResult();

        if (productType.getProductTypeName() != null){
            if (productType.getProductTypeName().trim().length() < 2 || productType.getProductTypeName().trim().length() > 10){
                jsonResult.setMessage("分类名称长度在2-10位之间.");
                jsonResult.setErrorCode("500");
                return jsonResult;
            }
        }else {
            jsonResult.setMessage("请输入分类名称.");
            jsonResult.setErrorCode("200");
            return  jsonResult;
        }

        if (productType.getProductTypeRank() != null){
            if (productType.getProductTypeRank() < 0 || productType.getProductTypeRank() > 3){  //判断分类等级是否为1-3等
                jsonResult.setMessage("分类等级异常，请刷新页面后重试.");
                jsonResult.setErrorCode("500");
                return jsonResult;
            }

            if (productType.getProductTypeRank() >1 ){ //判断分类为二或三等时，父级类代码是否为空
                if (productType.getProductTypeRank() == 2 && productType.getSuperType() == null){
                    jsonResult.setMessage("请选择2级分类");
                    jsonResult.setErrorCode("500");
                    return  jsonResult;
                }

                if (productType.getProductTypeRank() == 3 && productType.getSuperType() == null){
                    jsonResult.setMessage("请选择3级分类");
                    jsonResult.setErrorCode("500");
                    return  jsonResult;
                }
            }

        }else {
            jsonResult.setMessage("请选择分类等级.");
            jsonResult.setErrorCode("200");
            return  jsonResult;
        }
        if (productType.getSuperType() == null) productType.setSuperType("0");

        //判断分类代码是否存在数据库
        ProductType getSuperTypeCode = productTypeDao.getProductTypeBy_OneColumn("productTypeCode",productType.getSuperType());
        if (getSuperTypeCode == null){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("该父类不存在!! 请刷新页面后重试.");
            return  jsonResult;
        }

        int code = (int)(Math.random()*(9999-1000+1))+1000;//随机生成1000-9999的4为数字

        productType.setProductTypeCode(String.valueOf(code));

        productTypeDao.addProductType(productType);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("添加成功.");

        return  jsonResult;
    }
































}
