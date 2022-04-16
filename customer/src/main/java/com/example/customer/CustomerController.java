package com.example.customer;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.customer.security.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public record CustomerController(CustomerService customerService,
                                 JWTUtils jwtUtils) {

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

    @GetMapping(path = "refresh-token")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        log.info("refresh token request {}", request.getHeader(AUTHORIZATION));
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(JWTUtils.BEARER)){
            try {
                String refresh_token = authorizationHeader.substring(JWTUtils.BEARER.length());

                DecodedJWT parseToken = jwtUtils.parseToken(refresh_token);
                UserDetails userDetails = customerService.loadUserByUsername(parseToken.getSubject());
                Map<String, String> tokens = jwtUtils.refreshTokens(userDetails, refresh_token, request.getRequestURL().toString());

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception ex){
                log.error("Error logging in: {}", ex.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> errors = new HashMap<>();
                errors.put("errors", ex.getMessage());
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }

        }else {
            throw new IllegalArgumentException("refresh token is missing!");
        }
    }

}
