package cn.lcvc.service;

import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.ProductImg;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.ProductDao;
import cn.lcvc.dao.ProductImgDao;
import cn.lcvc.dao.UserDao;
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
    @Autowired
    private ProductImgDao productImgDao;
    @Autowired
    private UserDao userDao;
    /**
     * 商品发布
     * @param product 一个商品实体
     * @param sessionUser Session中的用户对象
     * @param urls 商品图片路径集合
     */
    public JsonResult issueProduct(Product product,User sessionUser,List<String> urls) {
        JsonResult jsonResult=new JsonResult();

        if(product.getProductName().trim().length()<2||product.getProductName().trim().length()>30)
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
            product.setGrounding(true);
        }
        if(product.getBuyTime()==null)
        {
            jsonResult.setMessage("入手时间必须填写");
            return jsonResult;
        }

        product.setCriateTime(new Timestamp(System.currentTimeMillis()));
        product.setUser(sessionUser);
        product.setSeeNumber(0);
        product.setState(false);
        product.setImgUrl(urls.get(0));//选取第一张图片
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
     * @param id 用户的Id
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



    /**********************************    @wurubiao  2017-12-10 15:42:24   *************************************/

     /*
    * 全站点商品管理,
    * @param product:指的是商品对象  lowProductNumber,hiProductNumber：最低库存,最高库存  , lowProductPrice,hiProductPrice: 最低价，最高价，lowSeeNumber,hiSeeNumber: 最低浏览量，最高浏览量
    * 注意:如果传入的对象的值或其他值为空，则不存进map。否则就存进map集合中,然后传到dao层对应方法根据map集合的Key,Value进行对应的查询
    * @return 返回查询结果
    * */
    public JsonResult getAllProductManage(Product product,Integer lowProductNumber,Integer hiProductNumber,Double lowProductPrice,Double hiProductPrice,Integer lowSeeNumber,Integer hiSeeNumber){
        JsonResult jsonResult = new JsonResult();
        Map<Object, Object> map = new HashMap<Object, Object>(); //执行数据库查询所用Map容器
        Map<Object,Object> map_product= new HashMap<Object, Object>(); //存放查询所得数据

//        id 序号
//        productName	商品名称
//        productNumber	商品库存
//        productType	所属分类
//        productPrice	商品价格
//        school	所属校园
//        grounding	是否上架
//        user	发布用户
//        seeNumber	浏览数量


        //对product进行非空验证，如不为空则存进map集合传到dao层进行数据库查询
        if (product != null){
            if (product.getId() != null && product.getId() > 0) map.put("id",product.getId()); //eq
            if (product.getProductName() != null && product.getProductName().trim().length() !=0){ //like
                map.put("productName",product.getProductName());
            }
           /*分类*/
           if (product.getProductType() != null && product.getProductType().getId() !=0 ){//eq
               //此处应查询此分类代码是否存在 ....
               map.put("productType",product.getProductType());
           }
           //学校
           if (product.getSchool() != null && product.getSchool().getId() != 0) map.put("school",product.getSchool());//eq
           //是否上架
            if (product.getGrounding() != null) map.put("grounding",product.getGrounding());//eq
            //发布用户
            if (product.getUser() != null){
                if (product.getUser().getId() != null && product.getUser().getId() >0){
                    map.put("user",product.getUser());//eq
                }else if (product.getUser().getUserName() != null && product.getUser().getUserName().trim().length() !=0){
                    User user = userDao.getUserBy_OneColumn("userName",product.getUser().getUserName());//eq
                    if (user != null) map.put("user",user);
                }
            }

        }
           /*库存*/
        if (lowProductNumber != null && lowProductNumber >0) map.put("lowProductNumber",lowProductNumber);
        if (hiProductNumber != null && hiProductNumber >0) map.put("hiProductNumber",hiProductNumber);
        //价格区间
        if (lowProductPrice != null && lowProductPrice > 0.00) map.put("lowProductPrice",lowProductPrice);
        if (hiProductPrice != null && hiProductPrice > 0.00) map.put("hiProductPrice",hiProductPrice);
        //浏览量
        if (lowSeeNumber != null && lowSeeNumber >0) map.put("lowSeeNumber",lowSeeNumber);
        if (hiSeeNumber != null && hiSeeNumber >0) map.put("hiSeeNumber",hiSeeNumber);

        List<Product> list = productDao.getProductList(Product.class,map);

        if (list.size() > 0){
            map_product.put("product",list);

            jsonResult.setErrorCode("200");
            jsonResult.setMessage("查询成功.");
            jsonResult.setItem(map_product);
        }else {
            jsonResult.setMessage("500");
            jsonResult.setMessage("查询失败:无数据或出错.");
        }

        return  jsonResult;
    }


    /*
    * 全站点商品删除，（由于主键约束，故此功能只做虚拟删除，不做物理删除）此功能为全站点商品的删除，涉及到用户的个人利益以及企业信誉，故该功能在一般情况下不可使用，如许使用该功能，则需有高级管理员权限
    * @param product:指的是需要删除商品的对象
    * @return 如拥有高级管理员权限，则执行操作并返回删除结果、如无权限删除则提示无权限；不管是否有权删除，执行该功能完毕后，把操作该功能的管理员记录，利于日后查询。
    * */
    public  JsonResult deleteProduct(Product product){
        JsonResult jsonResult = new JsonResult();

        if (product != null && product.getId() != null){
            Product productOld= productDao.getProduct(product.getId());
            if (productOld != null){
                productOld.setState(true);//虚拟删除
                //List<ProductImg> productImg = productImgDao.getProductImgList("product",productOld);//查询productimg表中是否存在此商品图片
                productDao.updateProduct(productOld);
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端:删除失败，该商品不存在.");
                return  jsonResult;
            }

            if (productDao.getProduct(product.getId()).getState()){
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("服务端:删除成功.");
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端:删除失败.请重试.");
            }

        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端:删除异常，请刷新该页面后重试!!.");
            return  jsonResult;
        }
        //xxxxx
        return  jsonResult;
    }



    public  JsonResult getBlock_one(){
        JsonResult jsonResult=new JsonResult();

        List<Product> list = productDao.getProductRandLimit();//猜你喜欢模块  随机商品
        list.addAll(0,productDao.getFirstProduct());//最新出售模块 product表前4项
        list.addAll(10,productDao.getNewLeave());// 全新闲置模块  新旧程度位99新项
        list.addAll(16,productDao.getTopSeeNumber()); //最热商品
        if (list.size() >0){
            jsonResult.setErrorCode("200");
            jsonResult.setMessage("ok");
            jsonResult.setList(list);
        }

        return  jsonResult;
    }


    public JsonResult getProduct(Integer pid,Boolean pageIndex) throws NullPointerException{
        JsonResult jsonResult=new JsonResult();
        Map<Object, Object> map = new HashMap<Object, Object>();
        Product product=null;
        if(pid != null && pid > 0){
            product= productDao.getProduct(pid);
        }

        List<Product> list=productDao.getProductRandLimit();//推荐模块  随机商品

        jsonResult.setList(list);
        if(product != null && product.getId() != null){
                map.put("product",product);
            if (pageIndex){//判断是否首次浏览商品
                product.setSeeNumber(product.getSeeNumber()+1);
                productDao.updateProduct(product);
            }

        }
        jsonResult.setItem(map);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("查询成功");

        return  jsonResult;
    }
}
