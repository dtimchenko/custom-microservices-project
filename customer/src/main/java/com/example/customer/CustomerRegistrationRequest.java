package com.example.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record CustomerRegistrationRequest(
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @Email String email,
        @NotEmpty @Size(min = 6, max = 30) String password) {
}
