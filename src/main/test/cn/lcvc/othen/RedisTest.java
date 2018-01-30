package cn.lcvc.othen;

import cn.lcvc.POJO.User;
import cn.lcvc.service.BaseJunit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RedisTest extends BaseJunit {
    //Redis存List对象思路
    @Test
    public void redis()
    {
        //链接Redis
        Jedis jedis=new Jedis("localhost");
        List<User> users=new ArrayList<User>();
        //往users里面add三条记录
        for(int i=0;i<3;i++)
        {
            User user=new User();
            user.setUserName("哈哈哈");
            user.setId(10+i);
            users.add(user);
        }

        //创建Gson对象
        Gson gson=new Gson();

        //将users转换为json
        String json=gson.toJson(users);

        //将json存如Redis
        jedis.set("list",json);

        //将json从Redis取出
        String redisJson=jedis.get("list");

        //将redisJson转换为List<User>
        List<User> users1=gson.fromJson(redisJson,new TypeToken<List<User>>(){}.getType());

        //检查转换成功的List<User>
        for (int i = 0; i < users1.size(); i++) {
            User user =  users1.get(i);
            System.out.println(user.getUserName());
        }
    }

    /**
     * 删除单个文件
     *
     @return 单个文件删除成功返回true，否则返回false
     */
    @Test
    public  void deleteFile() {
//        String fileName = "G:\\IDEAPoject\\eh\\classes\\artifacts\\eh_war_exploded/eh_admin/image/3679327.jpeg";
//        File file = new File(fileName);
//        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
//        if (file.exists() && file.isFile()) {
//            if (file.delete()) {
//                System.out.println("删除单个文件" + fileName + "成功！");
//
//            } else {
//                System.out.println("删除单个文件" + fileName + "失败！");
//
//            }
//        } else {
//            System.out.println("删除单个文件失败：" + fileName + "不存在！");
//
//        }
        System.out.println();
    }
}
