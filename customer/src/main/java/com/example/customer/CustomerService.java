package com.example.customer;

import com.example.clients.fraud.FraudCheckResponse;
import com.example.clients.fraud.FraudClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record CustomerService(CustomerRepository customerRepository,
                              FraudClient fraudClient) {
    public void registerCustomer(CustomerRegistrationRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        assert fraudCheckResponse != null;

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("Fraudster!");
        }
    }

    public Optional<Customer> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }
}
