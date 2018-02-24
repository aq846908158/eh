package cn.lcvc.service;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ProductType;
import cn.lcvc.dao.AdminPermissionsDao;
import cn.lcvc.dao.ProductDao;
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
    private ProductDao productDao;
    @Autowired
    private AdminPermissionsDao adminPermissionsDao;

    /*
   * 产品类型管理
   * @param productType 产品类型对象
   * @return json
   * */
    public  JsonResult getProductType(ProductType productType,String sort,String sortType){
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

        List<ProductType> list = productTypeDao.getProductTypeList(ProductType.class,map,sort,sortType);

        if (list.size() > 0){
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("查询成功.");
            jsonResult.setList(list);
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端:无数据.");
        }

        return  jsonResult;
    }

    /*
     * 添加产品类型
     * @param productType 产品类型对象
     * @return json
     * */
    public JsonResult registerProductType(ProductType productType,Integer superId){
        JsonResult jsonResult = new JsonResult();
//验证分类信息------------------------------------------------------------------------------------------------
        if (productType.getProductTypeName() != null){
            if (productType.getProductTypeName().trim().length() < 2 || productType.getProductTypeName().trim().length() > 10){
                jsonResult.setMessage("分类名称长度在2-10位之间");
                jsonResult.setErrorCode("500");
                return jsonResult;
            }
        }else {
            jsonResult.setMessage("请输入分类名称");
            jsonResult.setErrorCode("200");
            return  jsonResult;
        }


        if (productType.getProductTypeRank() != null){
            if (productType.getProductTypeRank() < 0 || productType.getProductTypeRank() > 3){  //判断分类等级是否为1-3等
                jsonResult.setMessage("分类等级异常，请刷新页面后重试");
                jsonResult.setErrorCode("500");
                return jsonResult;
            }
        }else {
            jsonResult.setMessage("请选择分类等级");
            jsonResult.setErrorCode("500");
            return  jsonResult;
        }
//验证分类信息--------end---------------------------------------------------------------------------------------------------

        //code重复判断
        int code = (int)(Math.random()*(999999-100000+1))+100000;//随机生成100000-999999的6为数字
        while (true) {
            if (typeCodeRepeat(code)) {
                code = (int) (Math.random() * (999999 - 100000 + 1)) + 100000;//随机生成100000-999999的6为数字
                continue;
            }
            break;
        }
        //确定无重复后
        if(productType.getProductTypeRank()==1)/**如果添加的是一级分类*/
        {
            productType.setSuperType("0");
            productType.setProductTypeCode(String.valueOf(code));
            productTypeDao.addProductType(productType);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("添加成功");
            return  jsonResult;
        }
        else/**如果添加的不是一级分类*/
        {
            if(superId==null||superId==0)
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("请选择上级分类");
                return  jsonResult;
            }
            ProductType superProductType=productTypeDao.getProductType(superId);
            if(productType.getProductTypeRank()==3)
            {
                //如果添加的是三级分类  则对其校验只允许关联到二级分类
                if(superProductType.getProductTypeRank()==2)
                {
                    //校验通过后
                    productType.setProductTypeCode(String.valueOf(code));
                    productType.setSuperType(superProductType.getProductTypeCode());
                    productTypeDao.addProductType(productType);
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("添加成功");
                    return  jsonResult;

                }
                else
                {
                    //校验未通过
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("三级分类的父类只能是二级分类");
                    return  jsonResult;
                }
            }
            else
            {
                //如果添加的是三级分类  则对其校验只允许关联到二级分类
                if(superProductType.getProductTypeRank()==1)
                {
                    //校验通过后
                    productType.setProductTypeCode(String.valueOf(code));
                    productType.setSuperType(superProductType.getProductTypeCode());
                    productTypeDao.addProductType(productType);
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("添加成功");
                    return  jsonResult;
                }
                else
                {
                    //校验未通过
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("二级分类的父类只能是一级分类");
                    return  jsonResult;
                }
            }
        }


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

                List<ProductType> list=new ArrayList<ProductType>();//当前分类子分类集合
                List<Product> products=  productDao.getProductsBy_OneColumn("productType",productType);
                if (productType.getProductTypeRank() <3){
                    list = productTypeDao.getProductTypeByList_OneColumn("superType",productType.getProductTypeCode());

                    for (int i = 0; i < list.size(); i++) {//将所有当前分类和其子分类下的商品全部查出
                        ProductType productTypeT =  list.get(i);
                        products.addAll(productDao.getProductsBy_OneColumn("productType",productTypeT));
                    }
                }



                        for (int i = 0; i < products.size(); i++) {
                            ProductType temp=new ProductType();
                            temp.setId(1);
                            Product product =  products.get(i);
                            product.setState(true);
                            product.setProductType(temp);
                            productDao.updateProduct(product);
                        }
                        for (int i = 0; i < list.size(); i++) {
                            ProductType type =  list.get(i);

                            productTypeDao.deleteProductType(type);
                        }
                        productTypeDao.deleteProductType(productType);
                        jsonResult.setErrorCode("200");
                        jsonResult.setMessage("删除成功");



            }else{
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("删除失败:该产品类型不存在");
            }
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("删除失败:请刷新页面后重试");
        }


        return  jsonResult;
    }

    public  JsonResult getProductType_ByRank(Integer rank,Integer superId){
        JsonResult  jsonResult = new JsonResult();

        if(rank == 0 && superId== 0){
            List<ProductType> productTypeList=productTypeDao.getProductTypeList();
            jsonResult.setList(productTypeList);
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("获取所有分类成功");
            return  jsonResult;
        }
        if(superId==null||superId==0)
        {
                List<ProductType> productTypes=productTypeDao.getProductTypeByList_OneColumn("productTypeRank",rank);
                jsonResult.setList(productTypes);
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("获取成功");
                return  jsonResult;

        }else{
            if(superId!=null&&superId!=0)
            {
                ProductType productType=productTypeDao.getProductType(superId);
                if(productType!=null)
                {
                    List<ProductType> productTypes=productTypeDao.getProductTypeByList_TwoColumn("productTypeRank",rank,"superType",productType.getProductTypeCode());
                    jsonResult.setList(productTypes);
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("获取成功");
                    return  jsonResult;
                }
                else
                {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("非法操作");
                    return  jsonResult;
                }

            }
            else
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("非法操作");
                return  jsonResult;
            }

        }


    }

    /**
     * 判断分类代码是否重复
     * @return 有重复返回true  没有重复返回false
     */
    public  boolean typeCodeRepeat(int code)
    {
        String temp=String.valueOf(code);
        ProductType productType=productTypeDao.getProductTypeBy_OneColumn("productTypeCode",temp);
        if(productType==null)
        {
            return false;
        }
        else {
            return  true;
        }
    }

    /**
     * 查询分类是否存在
     * @param id
     * @return
     */
    public ProductType isTypeCode(Integer id){
        ProductType productType=new ProductType();

        productType=productTypeDao.getProductType(id);
        if (productType != null){
            return  productType;
        }

        return  productType;
    }

























}
