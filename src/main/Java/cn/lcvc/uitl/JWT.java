package cn.lcvc.uitl;

import cn.lcvc.POJO.User;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWT {
    static final String key="eh";
    public static String cretaToken(User user)throws IllegalArgumentException,UnsupportedEncodingException {

        Algorithm al = Algorithm.HMAC256(key);
        String token = com.auth0.jwt.JWT.create()
                .withIssuer("admin")
                .withSubject(user.getUserName())
                .withClaim("userid",user.getId() )
                .withExpiresAt(new Date(System.currentTimeMillis()+60000))
                .sign(al);
       return token;
    }

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
