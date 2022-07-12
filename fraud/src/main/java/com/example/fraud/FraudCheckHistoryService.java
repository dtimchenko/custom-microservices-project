package com.example.fraud;

import org.springframework.stereotype.Service;

@Service
public record FraudCheckHistoryService(FraudUserRepository fraudUserRepository) {

    public boolean isFraudulentCustomer(String email){
        return fraudUserRepository.findByEmail(email).isPresent();
    }
}