package com.summer.icommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.bouncycastle.crypto.tls.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

public class JwtUtils {

    private static final long EXPIRE_TIME = 30 * 60 * 1000;
	
	/**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            System.out.println("username"+jwt.getClaim("username").asString());
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
//    public static String sign(String username, String secret) {
//        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
//        Algorithm algorithm = Algorithm.HMAC256(secret);
//        // 附带username信息
//        return JWT.create()
//                .withClaim("username", username)
//                .withExpiresAt(date)
//                .sign(algorithm);
//    }
    /**
     * 生成签名,expireTime后过期
     * @param username 用户名
     * @param time 过期时间s
     * @return 加密的token
     */
    public static String sign(String username, String salt, long time) {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。

        try {
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(salt);
            // 附带username信息
            return JWT.create()
//                    .withJWTId("1") //// 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                    .withClaim("username", username)  /** token添加自定义属性 **/
                    .withExpiresAt(date) //设置过期时间-一分钟
                   .withIssuedAt(new Date())  //登录时间-也就是签发时间（签发给你token的时间）
                    .sign(algorithm); //设置签名秘钥
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * token是否过期
     * @return true：过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    /**
     * 生成随机盐,长度32位
     * @return
     */
    public static String generateSalt(){
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(16).toHex();
        return hex;
    }
    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        boolean flag = true;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }
}
