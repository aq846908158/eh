package cn.lcvc.controller;

import cn.lcvc.uitl.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "/uploader")
public class UploaderContorller {


    @ResponseBody
    @RequestMapping(value = "/fileImg")
    public JsonResult fileImgUploader(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {
        JsonResult jsonResult=new JsonResult();
            if(file != null && !file.isEmpty()){
                String path="";
                String pathRoot = httpServletRequest.getSession().getServletContext().getRealPath("");

                try {
                    //获得文件类型（可以判断如果不是图片，禁止上传）
                    String contentType = file.getContentType();
                    //获得文件后缀名称
                    String imageName = contentType.substring(contentType.indexOf("/") + 1);
                    String fileName = (int)(Math.random() * 10000000) + "." + imageName;
                    path = "/eh_admin/image/" + fileName;



                    System.out.println("路径 "+pathRoot + path);
                    file.transferTo(new File(pathRoot + path));

                    jsonResult.setMessage(pathRoot + path);

                }catch (Exception e){
                    System.out.println(e);
                }
            }


            return  jsonResult;



        }


}