package com.example.customer.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public record CustomerUpdateRequest(
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @Email String email) {
}
