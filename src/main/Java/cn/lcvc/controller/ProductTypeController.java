package cn.lcvc.controller;

import cn.lcvc.POJO.Order;
import cn.lcvc.POJO.ProductType;
import cn.lcvc.service.ProductTypeService;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/admin")
public class ProductTypeController {

    @Autowired
    ProductTypeService productTypeService;

    /**
     * 查询所有分类信息
     * @param productType
     * @param sort 需要排序的字段
     * @param sortType 排序类型【asc || desc】
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/productType",method =  RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult getProductTypeMessage(ProductType productType, @RequestParam(value = "sort") String sort, @RequestParam(value = "sortType") String sortType) throws UnsupportedEncodingException {
        byte[] b=productType.getProductTypeName().getBytes("ISO-8859-1");
        String productTypeName=new String(b,"utf-8");//采用utf-8去接string
        productType.setProductTypeName(productTypeName);

        JsonResult jsonResult = productTypeService.getProductType(productType,sort,sortType);
        return jsonResult;
    }

    /**
     * 删除一条分类信息
     * @param productType 分类信息对象，存放着要删除的分类ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/productType",method =  RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    public JsonResult deleteProductType(ProductType productType)
    {
        JsonResult jsonResult = productTypeService.deleteProductType(productType.getId());
        return jsonResult;
    }


    /**
     * 根据分类等级查询分类信息
     * @param rank 要查询的分类等级
     * @param superId  父分类ID（ 如果是二级分类 要查的是哪个一级分类下的二级分类）【如果只想查所有二级分类可以传空参数】
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/productRank",method =  RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public JsonResult getProductType_by_Rank( @RequestParam(value = "rank") Integer rank,@RequestParam(value = "superId") Integer superId)
    {
        JsonResult jsonResult = productTypeService.getProductType_ByRank(rank,superId);
        return jsonResult;
    }


    @ResponseBody
    @RequestMapping(value = "/productRank",method =  RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult addProduct( ProductType productType,@RequestParam(value = "superId") Integer superId)
    {
        JsonResult jsonResult = productTypeService.registerProductType(productType,superId);
        return jsonResult;
    }
}
