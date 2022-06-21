package com.example.customer.service;

import com.example.clients.fraud.FraudClient;
import com.example.customer.data.Customer;
import com.example.customer.data.CustomerRepository;
import com.example.customer.web.CustomerUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record CustomerService(CustomerRepository customerRepository,
                              FraudClient fraudClient) {

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
