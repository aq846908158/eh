package cn.lcvc.uitl;

import cn.lcvc.POJO.Admin;
import cn.lcvc.POJO.User;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
                .withClaim("userid",user.getId() ) //
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
                .withClaim("adminid",admin.getId() ) //
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


}
