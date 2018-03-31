package cn.lcvc.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UploaderService {


    /**
     * 获得一个UUID,生成作为图片文件名
     * @return String UUID
     */
    public  String getUUID(){
        String uuid = UUID.randomUUID().toString();
//去掉“-”符号
        return uuid.replaceAll("-", "");
    }
}