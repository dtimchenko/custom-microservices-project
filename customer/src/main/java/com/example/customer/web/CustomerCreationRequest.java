package com.example.customer.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record CustomerCreationRequest (
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotNull UUID userId,
        @Email String email) {
}