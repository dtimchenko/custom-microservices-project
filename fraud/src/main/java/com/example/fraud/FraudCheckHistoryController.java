package com.example.fraud;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
public record FraudCheckHistoryController(FraudCheckHistoryService fraudCheckHistoryService) {

    @GetMapping("{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId){
        boolean fraudulentCustomer = fraudCheckHistoryService.isFraudulentCustomer(customerId);
        return new FraudCheckResponse(fraudulentCustomer);
    }
}
