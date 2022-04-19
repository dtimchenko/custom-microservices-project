package com.example.clients.fraud;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "fraud")
public interface FraudClient {

    @GetMapping("api/v1/fraud-check/{customerId}")
    @CircuitBreaker(name = "fraud", fallbackMethod = "isFraudsterFallBack")
    FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId);

    default FraudCheckResponse isFraudsterFallBack(Integer customerId, Throwable exception){
        return new FraudCheckResponse(false);
    }
}
