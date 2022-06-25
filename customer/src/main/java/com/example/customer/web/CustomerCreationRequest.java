package com.example.customer.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

public record CustomerCreationRequest (
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotEmpty UUID userId,
        @Email String email,
        @NotEmpty @Size(min = 6, max = 30) String password) {
}