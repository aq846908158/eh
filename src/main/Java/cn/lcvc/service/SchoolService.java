package cn.lcvc.service;

import cn.lcvc.POJO.School;
import cn.lcvc.dao.SchoolDao;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *@Author @wuruibao
 *@Date 2017-12-12 09:02:53
*/
@Service
public class SchoolService {

    @Autowired
    private SchoolDao schoolDao;


    /**
     * @wuruibao
     *校园管理 功能
     * school:一个对象，根据schoolName进行查询
     */
    public JsonResult getSchoolManage(School school){
        JsonResult jsonResult = new JsonResult();
        Map<Object,Object> map_school= new HashMap<Object, Object>(); //存放查询所得数据
        List<School> schoolList=new ArrayList<School>();
        if (school != null){
            if (school.getSchoolName() != null && school.getSchoolName().trim().length() != 0){
                schoolList=schoolDao.getSchoolBy_OneColumnLike("schoolName",school.getSchoolName());
            }else {
                schoolList=schoolDao.getSchoolList();
            }
        }else {
            schoolList=schoolDao.getSchoolList();
        }

        if (schoolList.size() > 0){
            map_school.put("school",schoolList);

            jsonResult.setErrorCode("200");
            jsonResult.setMessage("服务端:查询成功.");
            jsonResult.setItem(map_school);
        }else {
            jsonResult.setMessage("500");
            jsonResult.setMessage("服务端:查询失败!无数据或出错.");
        }


        return  jsonResult;
    }










}
