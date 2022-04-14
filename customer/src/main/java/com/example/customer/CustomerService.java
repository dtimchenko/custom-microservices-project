package com.example.customer;

import com.example.clients.fraud.FraudCheckResponse;
import com.example.clients.fraud.FraudClient;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public record CustomerService(CustomerRepository customerRepository,
                              EntityManager entityManager,
                              FraudClient fraudClient) {
    public Customer registerCustomer(CustomerRegistrationRequest customerRequest) {
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

        return customer;
    }

    public Optional<Customer> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> updateCustomer(Integer customerId, CustomerUpdateRequest customerRequest){
        Optional<Customer> existingCustomer = customerRepository.findById(customerId);

        return existingCustomer
                .map(customer -> {

                    customer.setFirstName(customerRequest.firstName());
                    customer.setLastName(customerRequest.lastName());
                    customer.setEmail(customerRequest.email());

                    return customerRepository.save(customer);
                });
    }

    public void deleteCustomerById(Integer customerId) {
        customerRepository.deleteById(customerId);
    }
}
