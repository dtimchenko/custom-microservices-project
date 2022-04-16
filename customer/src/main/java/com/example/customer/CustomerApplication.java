package com.example.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@EnableFeignClients(basePackages = "com.example.clients")
@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //save test user in database
    @Bean
    CommandLineRunner run(CustomerService customerService){
        return args -> {
            CustomerRegistrationRequest testUser = new CustomerRegistrationRequest(
                    "test-user-first-name",
                    "test-user-last-name",
                    "test@email.com",
                    "1234567890"
            );

            customerService.createCustomer(testUser, false);
        };
    }

}
