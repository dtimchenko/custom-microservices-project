package com.example.customer.web;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FraudUserDto {
    private Integer id;
    private String email;
    private LocalDateTime createdAt;
}
