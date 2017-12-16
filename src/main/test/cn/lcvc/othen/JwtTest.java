package cn.lcvc.othen;

import cn.lcvc.POJO.TokenMessage;
import cn.lcvc.service.BaseJunit;


import cn.lcvc.uitl.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JwtTest extends BaseJunit {
    //JWT 中间部分解码思路
    @Test
    public void jwtTest() throws UnsupportedEncodingException {

        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTExMSIsImlzcyI6ImNvbS5laCIsImV4cCI6MTUxMzY2OTcxNCwidXNlcklkIjo4LCJpYXQiOjE1MTM0MTA1MTR9.VoV7x7chNa2KmGrfM6YPMgY5dS_JP_YUFWpJqlQ_DCk";
        TokenMessage t=JWT.getPayloadDecoder(token);
        System.out.println(t.getSub());
        System.out.println(t.getUserId());

    }


}
