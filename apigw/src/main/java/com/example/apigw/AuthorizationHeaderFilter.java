package com.example.apigw;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private static final String BEARER = "Bearer ";

    @Autowired
    private Environment environment;

    public static class Config {

    }

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Authorization token validation started.");

            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                log.error("No Authorization header provided");
                return onError(exchange, UNAUTHORIZED);
            }

            String token = request
                    .getHeaders()
                    .get(HttpHeaders.AUTHORIZATION)
                    .get(0)
                    .substring(BEARER.length());

            if(!isValidJWT(token)){
                log.error("Not a valid token");
                return onError(exchange, UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange,
                               HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    private boolean isValidJWT(String token){
        JWTVerifier verifier = JWT.require(algorithm()).build();
        try{
            return !verifier
                    .verify(token)
                    .getSubject()
                    .isEmpty();
        }catch (Exception exception){
            log.error("Token validation failed:{}, \nReason: {}", token, exception.getMessage());
            return false;
        }
    }

    private Algorithm algorithm(){
        return Algorithm.HMAC256(environment.getProperty("jwt.secret").getBytes());
    }

}
