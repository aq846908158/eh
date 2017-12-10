package cn.lcvc.service;

import cn.lcvc.POJO.ProductType;
import cn.lcvc.dao.AdminPermissionsDao;
import cn.lcvc.dao.ProductTypeDao;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*
   * 产品类型管理
   * @param productType 产品类型对象
   * @return json
   * */
    public  JsonResult getProductType(ProductType productType){
        JsonResult jsonResult = new JsonResult();
        Map<Object, Object> map = new HashMap<Object, Object>(); //查询所用Map容器
        Map<Object,Object> map_productType= new HashMap<Object, Object>(); //存放查询所得数据

        if (productType != null){
            //产品类型名称 like
            if (productType.getProductTypeName() !=null && productType.getProductTypeName().trim().length() != 0){
                map.put("productTypeName",productType.getProductTypeName());
            }
            //产品类型分类等级 eq
            if (productType.getProductTypeRank() != null && productType.getProductTypeRank() > 0 && productType.getProductTypeRank() < 4){
                map.put("productTypeRank",productType.getProductTypeRank());
            }
            //产品类型分类代码 eq
            if (productType.getProductTypeCode() != null && productType.getProductTypeCode().trim().length() == 6){
                map.put("productTypeCode",productType.getProductTypeCode());
            }
            //产品类型父级分类代码 eq
            if (productType.getSuperType() != null && productType.getSuperType().trim().length() == 6){
                map.put("superType",productType.getSuperType());
            }
        }

        List<ProductType> list = productTypeDao.getProductTypeList(ProductType.class,map);

        if (list.size() > 0){
            map_productType.put("productTypeList",list);

            jsonResult.setErrorCode("200");
            jsonResult.setMessage("查询成功.");
            jsonResult.setItem(map_productType);
        }else {
            jsonResult.setMessage("500");
            jsonResult.setMessage("查询失败:无数据.");
        }

        return  jsonResult;
    }

    /*
     * 添加产品类型
     * @param productType 产品类型对象
     * @return json
     * */
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
        if(productType.getProductTypeRank() != 1){
            ProductType getSuperTypeCode = productTypeDao.getProductTypeBy_OneColumn("productTypeCode",productType.getSuperType());
            if (getSuperTypeCode == null){
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("该父类不存在!! 请刷新页面后重试.");
                return  jsonResult;
            }
        }


        int code = (int)(Math.random()*(999999-100000+1))+100000;//随机生成100000-999999的6为数字
        //code重复判断

        productType.setProductTypeCode(String.valueOf(code));

        productTypeDao.addProductType(productType);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("添加成功.");

        return  jsonResult;
    }


    /*
     * 删除产品类型
     * @param typeId 产品类型ID
     * @return json
     * */
    public  JsonResult deleteProductType(Integer typeId){
        JsonResult  jsonResult = new JsonResult();

        if (typeId != null){
            //根据此id查询,并判断该id索引的对象是否存在数据库中
            ProductType productType = productTypeDao.getProductType(typeId);
            if (productType != null){

                List<ProductType> list=new ArrayList<ProductType>();
                if (productType.getProductTypeRank() !=3){
                    list = productTypeDao.getProductTypeByList_OneColumn("superType",productType.getProductTypeCode());
                }

                    if (list.size() > 0){
                        jsonResult.setErrorCode("500");
                        jsonResult.setMessage("删除失败:该产品类型下有父级分类,如需删除该分类请先删除所有所属父级分类产品类型.");
                    }else {
                        productTypeDao.deleteProductType(productType);
                        jsonResult.setErrorCode("500");
                        jsonResult.setMessage("服务端:删除成功.");
                    }


            }else{
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("删除失败:该产品类型不存在.");
            }
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("删除失败:请刷新页面后重试.");
        }


        return  jsonResult;
    }
































}
