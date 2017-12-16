package cn.lcvc.uitl;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.POJO.User;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

public class JWT {
    private  static final String key="eh";


    /**
     * 创建User  Token
     * @param user 已登录成功User对象
     * @return
     * @throws IllegalArgumentException
     * @throws UnsupportedEncodingException
     */
    public static String cretaToken(User user)throws IllegalArgumentException,UnsupportedEncodingException {
        Algorithm al = Algorithm.HMAC256(key); //签名
        String token = com.auth0.jwt.JWT.create()
                .withIssuer("com.eh") //发行者
                .withSubject(user.getUserName()) //主题
                .withClaim("userId",user.getId() ) //
                .withExpiresAt(new Date(System.currentTimeMillis()+259200000)) //过期时间 tonken保存时间为3天
                .withIssuedAt(new Date()) //发行时间
                .sign(al);//算法加密签名
       return token;
    }


    /**
     * 创建Admin  Token
     * @param admin 已登录成功User对象
     * @return
     * @throws IllegalArgumentException
     * @throws UnsupportedEncodingException
     */
    public static String cretaTokenOfAdmin(Admin admin)throws IllegalArgumentException,UnsupportedEncodingException {
        Algorithm al = Algorithm.HMAC256(key); //签名
        String token = com.auth0.jwt.JWT.create()
                .withIssuer("com.eh") //发行者
                .withSubject(admin.getUserName()) //主题
                .withClaim("adminId",admin.getId() ) //
                .withExpiresAt(new Date(System.currentTimeMillis()+259200000)) //过期时间 tonken保存时间为3天
                .withIssuedAt(new Date()) //发行时间
                .sign(al);//算法加密签名
        return token;
    }
    /**
     * 解密Token
     * @param token
     * @return 返回解密结果，true OR false
     */
    public static boolean verifyJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 解码JWT Payload部分
     * @param JWT JWT字符串
     * @return TokenMessag对象
     */
    public static TokenMessage getPayloadDecoder(String JWT)
    {
        //创建一个Base64 解码对象
        Base64.Decoder decoder=Base64.getDecoder();
        //取出Payload部分单独存放
        String jwtHeader=getJwtPayload(JWT);
        //使用Base64解码对象的decode方法将其解码并放
        String jiema="";
        try {
            jiema=new String(decoder.decode(jwtHeader),"UTF-8");

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        //创建Gson对象
        Gson gson=new Gson();
        //使用Gson对象将解码后的信息转换为TokenMessage对象；
        TokenMessage t=gson.fromJson(jiema,TokenMessage.class);

        return t;
    }


    //获取JWT Payload部分
    public static String getJwtPayload(String JWT)
    {
        String temp= StringUtils.substringBefore(JWT,".");
        String newJWT=JWT.replaceAll( temp+".","");
        newJWT=StringUtils.substringBefore(newJWT,".");
        return newJWT;
    }
}
