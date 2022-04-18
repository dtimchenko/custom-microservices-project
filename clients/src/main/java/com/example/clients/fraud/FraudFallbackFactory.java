package com.example.clients.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FraudFallbackFactory implements FraudClient, FallbackFactory<FraudClient> {

    @Override
    public FraudCheckResponse isFraudster(Integer customerId) {
        log.info("a fallback isFraudster check was called.");
        return new FraudCheckResponse(false);
    }

    @Override
    public FraudClient create(Throwable cause) {
        log.error("fraud isFraudster check has failed: {}", cause.getMessage());
        return this;
    }
}
