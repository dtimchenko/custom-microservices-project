package com.example.customer.facade;

import com.example.commons.protobuf.fraud.FraudServiceGrpc;
import com.example.commons.protobuf.fraud.FraudUserCreationRequest;
import com.example.commons.protobuf.fraud.FraudUserCreationResponse;
import com.example.customer.data.Customer;
import com.example.customer.service.CustomerService;
import com.example.customer.web.CustomerCreationRequest;
import com.example.customer.web.CustomerUpdateRequest;
import com.example.customer.web.FraudUserDto;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;

@Slf4j
@Component
public class CustomerFacade {

    @GrpcClient("grpc-fraud-service")
    private FraudServiceGrpc.FraudServiceBlockingStub fraudGrpcClient;
    @Autowired
    private CustomerService customerService;

    public FraudUserDto addFraudster(CustomerCreationRequest customerRequest){
        FraudUserCreationRequest fraudUserCreationRequest = FraudUserCreationRequest.newBuilder().setEmail(customerRequest.email()).build();
        FraudUserCreationResponse fraudUserCreationResponse = fraudGrpcClient.addFraud(fraudUserCreationRequest);

        log.info("fraud grpc response: [{}]", fraudUserCreationResponse );

        LocalDateTime createAt =
                LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(
                                fraudUserCreationResponse.getCreatedAt().getSeconds(),
                                fraudUserCreationResponse.getCreatedAt().getNanos()),
                        TimeZone.getDefault().toZoneId());

        return FraudUserDto.builder()
                .id(fraudUserCreationResponse.getId())
                .email(fraudUserCreationResponse.getEmail())
                .createdAt(createAt)
                .build();
    }

    public Customer createCustomer(CustomerCreationRequest customerRequest) {
        return customerService.createCustomer(customerRequest);
    }

    public Optional<Customer> getCustomerById(Integer customerId) {
        return customerService.getCustomerById(customerId);
    }

    public Optional<Customer> updateCustomer(Integer customerId, CustomerUpdateRequest customerRequest) {
        return customerService.updateCustomer(customerId, customerRequest);
    }

    public void deleteCustomerById(Integer customerId) {
        customerService.deleteCustomerById(customerId);
    }
}
