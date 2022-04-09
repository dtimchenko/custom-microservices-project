package com.example.fraud;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record FraudCheckHistoryService(FraudCheckHistoryRepository fraudCheckHistoryRepository) {

    public boolean isFraudulentCustomer(Integer customerId){
        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .isFraudster(false)
                        .customerId(customerId)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return false;
    }
}