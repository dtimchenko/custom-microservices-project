package com.example.customer.web;

import com.example.customer.data.Customer;
import com.example.customer.service.CustomerService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(path = "{customerId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Timed(value = "getCustomerById.time", description = "time to get an existing customer")
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
