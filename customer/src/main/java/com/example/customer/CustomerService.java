package com.example.customer;

import com.example.clients.fraud.FraudCheckResponse;
import com.example.clients.fraud.FraudClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public record CustomerService(CustomerRepository customerRepository,
                              FraudClient fraudClient,
                              BCryptPasswordEncoder passwordEncoder) implements UserDetailsService {

    public Customer registerCustomer(CustomerRegistrationRequest customerRequest) {
        return createCustomer(customerRequest, true);
    }

    public Customer createCustomer(CustomerRegistrationRequest customerRequest, boolean withFraudCheck) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .encryptedPassword(passwordEncoder.encode(customerRequest.password()))
                .build();

        customerRepository.saveAndFlush(customer);

        if(withFraudCheck){
            FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

            assert fraudCheckResponse != null;

            if(fraudCheckResponse.isFraudster()){
                throw new IllegalStateException("Fraudster!");
            }
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(username);
        if(Objects.isNull(customer)){
            throw new UsernameNotFoundException("user not found: " + username);
        }

        return new User(customer.getEmail(), customer.getEncryptedPassword(), Collections.emptyList());
    }
}
