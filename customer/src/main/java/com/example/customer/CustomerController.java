package com.example.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public record CustomerController(CustomerService customerService) {

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Customer> registerCustomer(@RequestBody @Valid CustomerRegistrationRequest customerRequest){
        log.info("new customer registration request {}", customerRequest);
        return ResponseEntity.ok(customerService.registerCustomer(customerRequest));
    }

    @GetMapping(path = "{customerId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Integer customerId){
        log.info("get customer by id {}", customerId);
        return customerService
                .getCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "{customerId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Customer> updateCustomer(@RequestBody @Valid CustomerUpdateRequest customerRequest,
                                                   @PathVariable("customerId") Integer customerId){
        log.info("customer update request {} for customer {}", customerRequest, customerId);
        return customerService.updateCustomer(customerId, customerRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomerById(@PathVariable("customerId") Integer customerId){
        log.info("delete customer by id {}", customerId);
        customerService.deleteCustomerById(customerId);
    }

}
