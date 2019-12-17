package com.capfsd.mod.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JWTUtils {

        public static final String KEY = "022bdc63c3c5a45879ee6581508b9d03adfec4a4658c0ab3d722e50c91a351c42c231cf43bb8f86998202bd301ec52239a74fc0c9a9aeccce604743367c9646b";

        public static SecretKey generalKey(){
            byte[] encodedKey = Base64.decodeBase64(KEY);
            SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

            return key;
        }


        public static String createJWT(String id, String issuer, String subject, long ttlMillis) throws Exception {

            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            Map<String, Object> claims = new HashMap<>();
            claims.put("uid", "12345678");
            claims.put("user_name", "fsd stock system");
            claims.put("nick_name", "fsd ss");
            SecretKey key = generalKey();
                  JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                    .setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                    .setId(id)                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                    .setIssuedAt(now)           // iat: jwt的签发时间
                    .setIssuer(issuer)          // issuer：jwt签发人
                    .setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                    .signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥
                  if (ttlMillis >= 0) {
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }
            return builder.compact();
        }


//        public static Claims parseJWT(String jwt) throws Exception {
//            SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
//            Claims claims = Jwts.parser()  //得到DefaultJwtParser
//                    .setSigningKey(key)                 //设置签名的秘钥
//                    .parseClaimsJws(jwt).getBody();     //设置需要解析的jwt
//            return claims;
//        }

//        private static Date getExpirationFromJWT(String jwt) {
//            try {
//                final Claims claims = parseJWT(jwt);
//                return claims.getExpiration();
//            } catch (Exception e) {
//                return null;
//            }
//        }
//
//        public static String getUsernameFromJWT(String jwt) {
//            try {
//                final Claims claims = parseJWT(jwt);
//                return claims.getSubject();
//            } catch (Exception e) {
//                return null;
//            }
//        }

        public static final String SIGNING_KEY = "ADB_3143532145abcd1143513";

        public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
        }

        public static Boolean validToken(String token) {
            try {
                final Claims claims = Jwts.parser()
                        .setSigningKey(SIGNING_KEY)
                        .parseClaimsJws(token)
                        .getBody();
                Date expiration = claims.getExpiration();
                Date now = new java.util.Date();
                Date notBefore = Optional.ofNullable(claims.getNotBefore()).orElse(now);
                if(expiration.before(now))
                    return false;
                else return true;
            } catch (Exception e) {
                return false;
            }
        }

}
