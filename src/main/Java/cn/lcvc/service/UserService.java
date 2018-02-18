package cn.lcvc.service;

import cn.lcvc.POJO.School;
import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.POJO.User;
import cn.lcvc.dao.SchoolDao;
import cn.lcvc.dao.UserDao;
import cn.lcvc.uitl.DataCheck;
import cn.lcvc.uitl.JWT;
import cn.lcvc.uitl.JsonResult;
import cn.lcvc.uitl.Md5;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.ObjectIdMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//注入Service
@Service
public class UserService{
    //注入Dao
    @Autowired
    private  UserDao userDao;
    @Autowired
    private SchoolDao schoolDao;

    /**
     *
     * @param user
     * @param confirmPassword 二次密码
     * @return
     */
    public JsonResult registerUser(User user,String confirmPassword) {
        JsonResult jsonResult=new JsonResult();
        if(userDao.getUserBy_OneColumn("userName",user.getUserName())!=null)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("用户名已存在");
            return jsonResult;
        }

        if(user.getUserName().length()<6||user.getUserName().length()>32)
        {

            jsonResult.setMessage("用户名格式错误!应在6-32字符以内");
            return jsonResult;
        }
        else if(user.getUserPassword().length()<8||user.getUserPassword().length()>32)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("密码格式错误!应在8-32字符以内");
            return jsonResult;
        }
        else if (!confirmPassword.equals(user.getUserPassword())){
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("两次密码不一致！请重新输入");
            return jsonResult;
        }
        else if(!DataCheck.isTrueName(user.getTrueName()))
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("真实姓名应该在2-4个中文字符以内");
            return jsonResult;
        }
//        else if(user.getSchool()==null||user.getSchool().getId()==0)
//        {
//            jsonResult.setErrorCode("500");
//            jsonResult.setMessage("请选择所在校园");
//            return jsonResult;
//        }
        else if(user.getPhone().length()!=11|| !DataCheck.isMobileNO(user.getPhone()))
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("手机号码格式不正确");
            return jsonResult;
        }
        else if(user.getEmail().length()<1||!DataCheck.isEmailNO(user.getEmail()))
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("邮箱格式不正确");
            return jsonResult;
        }


        user.setSalt(Md5.getRandomString(32));
        user.setUserPassword(Md5.MD5(user.getUserPassword()+user.getSalt()));
        user.setLastTime(new Timestamp(System.currentTimeMillis()));
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        user.setForSaleNumber(0);
        user.setSellNumber(0);
        user.setBanLogin(false);
        user.setBanSell(false);
        School school = new School();
        school.setId(1);
        user.setSchool(school);
        userDao.addUser(user);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("服务端：注册成功");

        return jsonResult;
    }//完成

    /**
     * 获取所有用户List列表
     * @return JsonResult数据 获取到的用户列表存储在JsonResult.item中  key为"users"
     */
    public JsonResult getAllUser() {
        JsonResult jsonResult=new JsonResult();
        List<User> users=userDao.getUserList();
        Map<Object,Object> item=new HashMap<Object,Object>();
        item.put("users",users);
        jsonResult.setItem(item);

        jsonResult.setErrorCode("200");
        jsonResult.setMessage("获取成功");
        return jsonResult;

    }//完成

    /**
     * 删除一个用户
     * @param id 用户的Id

     */
    public JsonResult deleteUser(Integer id) {
        JsonResult jsonResult=new JsonResult();
        if(id!=null&&id!=0) {
                User user=userDao.getUser(id);
                if(user!=null) {
                    userDao.deleteUser(user);
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("删除成功");
                    return  jsonResult;
                }else{
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("数据库中不存在此用户");
                    return jsonResult;
                }

        }

        jsonResult.setErrorCode("500");
        jsonResult.setMessage("删除失败");
        return jsonResult;

    }//完成

    /**
     * 修改一个用户的基本信息
     * @param user 一个用户实体
     * 注意!此方法只能对基本信息进行表单验证！特殊字段修改请不要使用此方法
     */
    public JsonResult baseMessageUpdate(User user) {
        JsonResult jsonResult=new JsonResult();
        if (user!=null && user.getId()!=null && userDao.getUser(user.getId())!=null)
        {

            if(!DataCheck.isTrueName(user.getTrueName()))
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("真实姓名应该在2-4个中文字符以内");
                return jsonResult;
            }
            else if(user.getSchool()==null||user.getSchool().getId()==0)
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("请选择所在校园");
                return jsonResult;
            }
            else if(schoolDao.getSchool(user.getSchool().getId())==null)
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("数据库中没有此校园");
                return jsonResult;
            }
            else if(user.getPhone().length()!=11|| !DataCheck.isMobileNO(user.getPhone()))
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("手机号码格式不正确");
                return jsonResult;
            }
            else if(user.getEmail().length()<1||!DataCheck.isEmailNO(user.getEmail()))
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("邮箱格式不正确");
                return jsonResult;
            }

                userDao.updateUser(user);
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("修改成功");
                return jsonResult;


        }

        jsonResult.setErrorCode("500");
        jsonResult.setMessage("修改失败");
        return  jsonResult;
    }//完成

    /**
     * 用户登录功能,判断用户名和密码是否正确、用户是否被封号,成功后修改最后登录时间
     * @param userName 用户名
     * @param userPassword  密码
     * @return JsonResult数据 如果登录成功（JsonResult.item）中会存放key为“user”的一个User对象用于让控制层存入Session
     */
    public  JsonResult login(String userName,String userPassword) {
        JsonResult jsonResult = new JsonResult();
        User user = userDao.getUserBy_OneColumn("userName", userName);
        if (user != null) {
            if (!user.getBanLogin()) {
                userPassword = Md5.MD5(userPassword + user.getSalt());

                if (user.getUserPassword().equals(userPassword)) {
                    Map<Object, Object> map = new HashMap<Object, Object>();
                    String token = "";
                    try {
                        token = JWT.cretaToken(user); //签发Token
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    map.put("token", token);//将Token放到map存进json中
                    map.put("userId", user.getId()); //将UserID 存到mao，然后通过json传到前台
                    Jedis jedis = new Jedis("localhost"); //连接本地的 Redis 服务
                    //System.out.println("服务正在运行: "+jedis.ping()); //查看服务是否运行
                    jedis.set(user.getId() + "_token", token); //将key = (id_token) value = (token) 键值对存入redis
                    jsonResult.setItem(map);//存入map集合
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("登录成功");
                    //修改最后登录时间
                    user.setLastTime(new Timestamp(System.currentTimeMillis()));
                    userDao.updateUser(user);
                    //将user对象传到控制层 以便于登录成功后在控制层中将登录成功的用户对象存入Session
                    Map<Object, Object> temp = new HashMap<Object, Object>();
                    temp.put("user", user);

                    return jsonResult;
                } else {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("用户名或密码输入错误");
                    return jsonResult;
                }
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("用户被禁止登录!");
                return jsonResult;
            }
        } else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("用户名不存在");
            return jsonResult;
        }

    }//完成

    /**
     * 修改用户密码
     * @param id 用户Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return JsonResult数据 如果修改成功 （JsonResult.message="修改成功"） 如果修改失败（JsonResult.message=错误信息）
     */
    public  JsonResult updateUserPassword(Integer id,String oldPassword,String newPassword)
    {
        JsonResult jsonResult=new JsonResult();
        User user=userDao.getUser(id);

        if(user!=null)
        {
            oldPassword=Md5.MD5(oldPassword+user.getSalt());
            if(user.getUserPassword().equals(oldPassword))
            {
                if(newPassword.length()<8||newPassword.length()>32)
                {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("密码格式错误!应在8-32字符以内");
                    return jsonResult;
                }
                else
                {
                    newPassword=Md5.MD5(newPassword+user.getSalt());
                    user.setUserPassword(newPassword);
                    userDao.updateUser(user);
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("密码修改成功");
                    return jsonResult;
                }
            }
            else
            {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("旧密码输入错误");
                return jsonResult;
            }

        }
        else
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("密码修改失败");
            return jsonResult;
        }

    }//完成

    /**
     * 修改用户禁止登录状态
     * @param id 用户ID
     * @param banNo 需要将用户禁止登录状态设置成为哪种状态
     * @return JsonResult对象 如若修改成功 （JsonResult.message="修改成功"）如若修改失败 （JsonResult.message=错误信息）
     */
    public JsonResult updateBanLogin(Integer id,Integer banNo) {
        JsonResult jsonResult =new JsonResult();
        User user=null;
        Boolean state=null;

        if (banNo == 0 || banNo == 1){
            if (banNo == 1) state=true;
            if (banNo == 0) state=false;
            user =userDao.getUser(id);
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：修改失败，请刷新后重试");
            return  jsonResult;
        }
        if (user !=null){
            if (user.getBanLogin() == state){
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("服务端：修改成功");
                return  jsonResult;
            }else {
                user.setBanLogin(state);
                userDao.updateUser(user);
                User userNeW=userDao.getUser(user.getId());
                if (userNeW.getBanLogin() == state){
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("服务端：修改成功");
                    return  jsonResult;
                }else {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端：修改失败，请刷新后重试");
                    return  jsonResult;
                }

            }

        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：修改状态失败，请重试");
            return  jsonResult;
        }
    }//完成

    /**
     * 修改用户禁止发布商品状态
     * @param id 用户ID
     * @param banNo 需要将用户禁止发布商品状态设置成为哪种状态
     * @return JsonResult对象 如若修改成功 （JsonResult.message="修改成功"）如若修改失败 （JsonResult.message=错误信息）
     */
    public JsonResult updateBanSell(Integer id,Integer banNo)
    {
        JsonResult jsonResult =new JsonResult();
        User user=null;
        Boolean state=null;

        if (banNo == 0 || banNo == 1){
            if (banNo == 1) state=true;
            if (banNo == 0) state=false;
            user =userDao.getUser(id);
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：修改失败，请刷新后重试");
            return  jsonResult;
        }
        if (user !=null){
            if (user.getBanSell() == state){
                jsonResult.setErrorCode("201");
                jsonResult.setMessage("服务端：修改成功");
                return  jsonResult;
            }else {
                user.setBanSell(state);
                userDao.updateUser(user);
                User userNeW=userDao.getUser(user.getId());
                if (userNeW.getBanSell() == state){
                    jsonResult.setErrorCode("200");
                    jsonResult.setMessage("服务端：修改成功");
                    return  jsonResult;
                }else {
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端：修改失败，请刷新后重试");
                    return  jsonResult;
                }

            }

        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：修改状态失败，请重试");
            return  jsonResult;
        }
    }//完成

    /**
     * 修改用户售出商品总数
     * @param id 用户Id
     * @param number 需要修改的数量
     * @return JsonResult对象 如若修改成功 （JsonResult.message="修改成功"）如若修改失败 （JsonResult.message=错误信息）
     */
    public JsonResult updateSellNumber(Integer id,Integer number)
    {
        JsonResult jsonResult=new JsonResult();
        User user=userDao.getUser(id);
        if(user==null)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("修改失败");
            return  jsonResult;
        }

        user.setSellNumber(number);
        userDao.updateUser(user);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("修改成功");
        return  jsonResult;
    }//完成


    /**
     * 修改用户在售商品总数
     * @param id 用户Id
     * @param number 需要修改的数量
     * @return JsonResult对象 如若修改成功 （JsonResult.message="修改成功"）如若修改失败 （JsonResult.message=错误信息）
     */
    public JsonResult updateForSaleNumber(Integer id,Integer number)
    {
        JsonResult jsonResult=new JsonResult();
        User user=userDao.getUser(id);
        if(user==null)
        {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("修改失败");
            return  jsonResult;
        }

        user.setForSaleNumber(number);
        userDao.updateUser(user);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("修改成功");
        return  jsonResult;
    }//完成

    public User getUser(Integer id)
    {
        return  userDao.getUser(id);
    }




    /**************************************    @author wuruibao      *************************************** **/

    /**
     *用户管理（后台管理）
     *@Author @wuruibao
     *@Date 2017/12/5 19:52
     *@params   user:User对象, lowSellNumber:最低销售成功数，hiSellNumber:最高销售成功数 lowForSaleNumber:最低在售, hiForSaleNumber:最高在售
     *@return   根据查询条件返回结果
    */
    public  JsonResult getAllUserManage(User user,Integer lowSellNumber,Integer hiSellNumber,Integer lowForSaleNumber,Integer hiForSaleNumber) {
        JsonResult jsonResult = new JsonResult();
        Map<String, Object> map = new HashMap<String, Object>(); //查询所用Map容器
        Map<Object, Object> map_Users = new HashMap<Object, Object>(); //存放查询所得数据

        if (user != null) {

            if (user.getUserName() != null && user.getUserName().trim().length() != 0) map.put("userName", user.getUserName().trim());//用户名
            if (user.getTrueName() != null && user.getTrueName().trim().length() != 0) map.put("trueName", user.getTrueName().trim());//真实姓名
            if (user.getBanLogin() != null){//登录状态
                if (user.getBanLogin() == true){
                    map.put("banLogin", true);
                }
                if (user.getBanLogin() == false){
                    map.put("banLogin", false);
                }
            }
            if (user.getBanSell() != null){//交易状态
                if (user.getBanSell() == true){
                    map.put("banSell", true);
                }
                if (user.getBanSell() == false){
                    map.put("banSell", false);
                }
            }

        }

        //销售成功数
        if (lowSellNumber != null && hiSellNumber != null) {
            if (lowSellNumber >= 0 && hiSellNumber >= 0) {
                map.put("lowSellNumber", lowSellNumber);
                map.put("hiSellNumber", hiSellNumber);
            }
        }

        //在售数量
        if (lowForSaleNumber != null && hiForSaleNumber != null) {
            if (lowForSaleNumber >= 0 && hiForSaleNumber >= 0) {
                map.put("lowForSaleNumber", lowForSaleNumber);
                map.put("hiForSaleNumber", hiForSaleNumber);
            }
        }
            List<User> users = userDao.getUser(User.class, map);

        if (users.size() > 0){
             jsonResult.setErrorCode("200");
            jsonResult.setMessage("服务端：查询成功.");
            jsonResult.setList(users);
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("服务端：无数据.");
        }
            return jsonResult;
    }


    /**
     *用户封号
     *@Author @wuruibao
     *@Date 2017/12/7 19:30
     *@params id：所需封号对象的ID  banLogin:禁止状态(true/false)
     *@return 如admin有权封号则返回封号结果，如无权限则提示无操作权限
    */
    public  JsonResult setUserBanLogin(Integer id,Boolean banLogin){
        JsonResult jsonResult = new JsonResult();
        if (id != null && banLogin != null) {
            User user=userDao.getUser(id);
            if (user != null) {
                user.setBanLogin(banLogin);
                userDao.updateUser(user);
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("登录封号异常.用户不存在.");
                return  jsonResult;
            }
            if (userDao.getUser(id).getBanLogin().equals(banLogin)){
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("登录封号成功.");
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("登录封号失败.");
            }
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("登录封号异常.");
        }

        return  jsonResult;
    }


    /**
     *用户交易封号
     *@Author @wuruibao
     *@Date 2017/12/7 19:52
     *@params   id:所需封号对象ID, banSell :  禁止状态(true/false)
     *@return 如admin有权封号则返回封号结果，如无权限则提示无操作权限
    */
    public JsonResult setUserBanSell(Integer id,Boolean banSell){
        JsonResult jsonResult = new JsonResult();

        if (id != null && banSell != null) {
            User user=userDao.getUser(id);
            if (user != null) {
                user.setBanSell(banSell);
                userDao.updateUser(user);
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("交易封号异常.用户不存在.");
                return  jsonResult;
            }
            if (userDao.getUser(id).getBanSell().equals(banSell)){
                jsonResult.setErrorCode("200");
                jsonResult.setMessage("交易封号成功.");
            }else {
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("交易封号失败.");
            }
        }else {
            jsonResult.setErrorCode("500");
            jsonResult.setMessage("交易封号异常.");
        }

        return  jsonResult;
    }



    public JsonResult repeatName(String userName) {
        JsonResult js=new JsonResult();
        User user =userDao.getUserBy_OneColumn("userName",userName.trim());
        if (user != null){
            js.setErrorCode("500");
            js.setMessage("用户名已存在");
            return  js;
        }else {
            js.setErrorCode("200");
            js.setMessage("用户名可用");
        }
        return  js;
    }


}
