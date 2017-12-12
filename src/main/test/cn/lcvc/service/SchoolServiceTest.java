package cn.lcvc.service;

import cn.lcvc.POJO.School;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SchoolServiceTest extends  BaseJunit{

    @Autowired
    private  SchoolService schoolService;

    @Test
    public  void getSchoolManageTest(){
        School school=new School();
        school.setSchoolName("城市");
        JsonResult jsonResult = schoolService.getSchoolManage(school);
        List<School> list = (List<School>) jsonResult.getItem().get("school");

        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i).getSchoolName());

        }

    }
}
