package com.example.customer.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtils {

    public static final String BEARER = "Bearer ";
    private static final long ACCESS_TOKEN_VALIDITY_SEC = 60 * 5;
    private static final long REFRESH_TOKEN_VALIDITY_SEC = 60 * 60 * 24 * 7;

    @Value(value = "jwt.secret")
    private String secret;

    public Map<String, String> generateTokens(UserDetails userDetails, String issuer){
        String access_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(ACCESS_TOKEN_VALIDITY_SEC)))
                .withIssuer(issuer)
                .sign(signatureAlgorithm());

        String refresh_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(REFRESH_TOKEN_VALIDITY_SEC)))
                .withIssuer(issuer)
                .sign(signatureAlgorithm());

        return Map.of(
                "access_token", access_token,
                "refresh_token", refresh_token
        );
    }

    public Map<String, String> refreshTokens(UserDetails userDetails,
                                             String refresh_token,
                                             String issuer){
        String access_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(ACCESS_TOKEN_VALIDITY_SEC)))
                .withIssuer(issuer)
                .sign(signatureAlgorithm());

        return Map.of(
                "access_token", access_token,
                "refresh_token", refresh_token
        );
    }

    public DecodedJWT parseToken(String token){
        JWTVerifier verifier = JWT.require(signatureAlgorithm()).build();
        return verifier.verify(token);
    }

    private Algorithm signatureAlgorithm(){
        return Algorithm.HMAC256(secret.getBytes());
    }
}
