package com.example.commons.clients.fraud;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fraud", configuration = OAuthFeignConfig.class)
public interface FraudClient {

    Logger log = LoggerFactory.getLogger(FraudClient.class);

    @GetMapping("api/v1/frauds/check/{email}")
    @Retry(name = "fraud")
    @CircuitBreaker(name = "fraud", fallbackMethod = "isFraudsterFallBack")
    FraudCheckResponse isFraudster(@PathVariable("email") String email);

    default FraudCheckResponse isFraudsterFallBack(String email, Throwable exception){
        log.info("isFraudsterFallBack method was executed for user {}", email);
        return new FraudCheckResponse(false);
    }
}
