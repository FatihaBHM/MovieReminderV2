package org.lafabrique_epita.exposition.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterDto (

    @NotBlank
    @NotNull
    @Pattern(regexp = "^\\p{L}[\\p{L}\\-_]{1,23}\\p{L}$")
    String username,

    @Email
    @NotNull
    @NotBlank
    String email,

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!\\-_]).{8,}$")
    String password
) {}
