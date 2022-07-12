package com.example.fraud;

import com.example.commons.clients.fraud.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/frauds/check")
public record FraudCheckHistoryController(FraudCheckHistoryService fraudCheckHistoryService) {

    @GetMapping("{email}")
    public FraudCheckResponse isFraudster(@PathVariable("email") String email){
        log.info("fraud check for customer {}", email);
        boolean fraudulentCustomer = fraudCheckHistoryService.isFraudulentCustomer(email);
        return new FraudCheckResponse(fraudulentCustomer);
    }
}
