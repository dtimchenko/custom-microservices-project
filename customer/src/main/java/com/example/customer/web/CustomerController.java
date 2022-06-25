package com.example.customer.web;

import com.example.customer.data.Customer;
import com.example.customer.service.CustomerService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("hasRole('admin') or #customerRequest.userId == T(java.util.UUID).fromString(#jwt.subject)")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Timed(value = "addCustomer.time", description = "time to add a new customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid CustomerCreationRequest customerRequest,  @AuthenticationPrincipal Jwt jwt){
        log.info("new customer add request {} from {}", customerRequest, jwt.getSubject());
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @PostAuthorize("returnObject.body != null ? returnObject.body.userId == T(java.util.UUID).fromString(#jwt.subject) : true")
    @GetMapping(path = "{customerId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Timed(value = "getCustomerById.time", description = "time to get an existing customer")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Integer customerId, @AuthenticationPrincipal Jwt jwt){
        log.info("get customer by id {}", customerId);
        return customerService
                .getCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('admin') or #customerRequest.userId == T(java.util.UUID).fromString(#jwt.subject)")
    @PutMapping(path = "{customerId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Customer> updateCustomer(@RequestBody @Valid CustomerUpdateRequest customerRequest,
                                                   @PathVariable("customerId") Integer customerId,
                                                   @AuthenticationPrincipal Jwt jwt){
        log.info("customer update request {} for customer {} from {}", customerRequest, customerId, jwt.getSubject());
        return customerService.updateCustomer(customerId, customerRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(path = "{customerId}")
    public void deleteCustomerById(@PathVariable("customerId") Integer customerId, @AuthenticationPrincipal Jwt jwt){
        log.info("delete customer by id {} from {}", customerId, jwt.getSubject());
        customerService.deleteCustomerById(customerId);
    }
}
