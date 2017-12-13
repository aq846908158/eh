package cn.lcvc.service;

import cn.lcvc.POJO.Message;
import cn.lcvc.POJO.Product;
import cn.lcvc.POJO.User;
import cn.lcvc.uitl.JsonResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageServiceTest extends  BaseJunit {
    @Autowired
    private  MessageService messageService;

    @Test
    public  void addMessageTest(){
        Message message=new Message();
        Product product=new Product();
        User user=new User();
        product.setId(3);
        user.setId(4);
        message.setText("完全OBJK");
        message.setSuperCode("790930");
        message.setProduct(product);
        message.setUser(user);
        JsonResult jsonResult=messageService.registerMessage(message);

        System.out.print(jsonResult.getMessage());

    }
}
