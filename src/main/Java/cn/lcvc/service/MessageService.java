package cn.lcvc.service;

import cn.lcvc.POJO.Message;
import cn.lcvc.dao.MessageDao;
import cn.lcvc.uitl.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;
    JsonResult jsonResult = new JsonResult();

    /**
     *用户留言功能
     */
    public JsonResult registerMessage(Message message){
        if (message != null){

            if (message.getText() == null || message.getText().trim().length() == 0){
                jsonResult.setMessage("服务端:留言失败,请输入留言内容.");
                jsonResult.setErrorCode("500");
                return  jsonResult;
            }

            if (message.getUser().getId() == null){
                jsonResult.setMessage("服务端:留言失败.请重新登录.");
                jsonResult.setErrorCode("500");
                return  jsonResult;
            }
            if (message.getProduct().getId() == null){
                jsonResult.setErrorCode("500");
                jsonResult.setMessage("服务端:留言失败,请刷新页面后重试");
                return  jsonResult;
            }
            if (message.getSuperCode() != null && message.getSuperCode().trim().length() ==6){ //如果父级留言代号不为空,则判断父级代号是否存在与message表中
                Message message_SuperCode = messageDao.getMessageBy_OneColumn("superCode",message.getSuperCode());
                if (message_SuperCode ==null){
                    jsonResult.setErrorCode("500");
                    jsonResult.setMessage("服务端:留言失败,请刷新页面后重试");
                    return  jsonResult;
                }
            }

        }
        int code=getMessageCode();

        message.setCode(String.valueOf(code));
        message.setCreateTime(new Timestamp(System.currentTimeMillis()));

        messageDao.addMessage(message);
        jsonResult.setErrorCode("200");
        jsonResult.setMessage("服务端:留言成功.");
        return  jsonResult;
    }

    public int getMessageCode(){
        int code = (int)(Math.random()*(999999-100000+1))+100000;//随机生成100000-999999的6为数字 作为留言代号
        Message message_eqCode = messageDao.getMessageBy_OneColumn("code",String.valueOf(code));
        //判断code是否重名
        if (message_eqCode !=null){
            getMessageCode();
        }
        return code;
    }










}
