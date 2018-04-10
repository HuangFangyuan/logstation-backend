package com.hfy.logstation.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hfy.logstation.exception.ResponseEnum;
import com.hfy.logstation.exception.ServerException;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    @Value("${serverSecret}")
    private static String secret;
    private static Algorithm algorithm = getHMAC256();
    private static JWTVerifier verifier = JWT.require(algorithm).withIssuer("fangyuan.net").build();

    private static Algorithm getHMAC256() {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(secret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return algorithm;
    }

    public static String generateToken(int uid) {
        Date expireDate = new Date(System.currentTimeMillis() + 24 * 3600 * 1000);
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(header)
                .withIssuer("fangyuan.net")
                .withExpiresAt(expireDate)
                .withClaim("uid", uid)
                .sign(algorithm);
        return token;
    }

    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        }
        catch (JWTVerificationException e) {
            throw new ServerException(ResponseEnum.INVALID_TOKEN);
        }
        if (jwt.getExpiresAt().before(new Date())) {
            throw new ServerException(ResponseEnum.EXPIRE_TOKEN);
        }

        return jwt.getClaims();
    }
}
