package cn.lcvc.controller;

import cn.lcvc.service.UploaderService;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UploaderService uploaderService;

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
                    String fileName=uploaderService.getUUID()+"."+imageName;
                    String homeUrl=System.getProperty("user.dir");//获取项目绝对路径
                    //！！！！特别注意  如果移动eh_home项目，请及时更改项目绝对路径
                    path = "G:\\IDEAPoject\\eh_home\\assets\\images\\productImg\\" + fileName;

//                    System.out.println("路径 "+path);
                    file.transferTo(new File(path));
                    jsonResult.setMessage(fileName);

                }catch (Exception e){
                    System.out.println(e);
                }
            }
            return  jsonResult;

        }


}