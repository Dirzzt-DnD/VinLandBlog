package com.warzero.vinlandblog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtils {

    //有效时间一个小时
    public static final long JWT_TTL = 15* 24 * 60 * 60 * 1000L;
    //设置密钥明文
    public static final String JWT_KEY = "Askeladdke";

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-","");
        return token;
    }

    public static String createJWT(String id, String subject, Long ttMillis){
        JwtBuilder jwtBuilder = getJWTBuilder(id,subject,ttMillis);
        return jwtBuilder.compact();
    }

    public static String createJWT(String subject, Long ttMillis){
        JwtBuilder jwtBuilder = getJWTBuilder(getUUID(),subject,ttMillis);
        return jwtBuilder.compact();
    }

    public static String createJWT(String subject){
        JwtBuilder jwtBuilder = getJWTBuilder(getUUID(),subject,null);
        return jwtBuilder.compact();
    }

    private static JwtBuilder getJWTBuilder(String id, String subject, Long ttMillis){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date();
        if(ttMillis == null){
            ttMillis = JwtUtils.JWT_TTL;
        }
        long expMillis = nowMillis+ttMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("Thorfinn")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm,secretKey)
                .setExpiration(expDate);
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtils.JWT_KEY);
        return new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
