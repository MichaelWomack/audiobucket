package com.mhw.audiobucket.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.mhw.audiobucket.exceptions.JwtException;
import com.mhw.audiobucket.config.AppConfig;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by mxw4182 on 12/26/16.
 */
public class JwtUtil {


    private static final String SECRET = "secretcode";
    private static final String ISSUER = "audiobucket";
    private static final int EXPIRATION_IN_MS = 60 * 60 * 1000;
    private Properties props;

    public static String createJWT(long userId) throws JwtException {


        try {
            JWTCreator.Builder jwtCreator = JWT.create();
            return jwtCreator
                    .withSubject(String.valueOf(userId))
                    .withIssuer(ISSUER)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_IN_MS))
                    .sign(Algorithm.HMAC256(SECRET));

        } catch (UnsupportedEncodingException e) {
            throw new JwtException("Failed to create token from userId " + userId, e);
        } catch (Exception e) {
            throw new JwtException("Failed to create token from userId " + userId, e);
        }
    }

    public static JWT verify(String token) throws JwtException {

        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(ISSUER)
                    .build();

            return (JWT) jwtVerifier.verify(token);
        } catch (UnsupportedEncodingException e) {
            throw new JwtException("Failed to verify token: " + token, e);
        }
    }

    public static void main(String[] args) throws JwtException {
        JWT jwt = verify("undefined");
        System.out.println(jwt);
    }
}
