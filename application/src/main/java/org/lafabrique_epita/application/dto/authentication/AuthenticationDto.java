package org.lafabrique_epita.application.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthenticationDto(


        @Schema(description = "L'adresse email de l'utilisateur", example = "utilisateur@gmail.fr")
        @Email
        @NotNull
        @NotBlank
        String email,

        @Schema(description = "Le mot de passe de l'utilisateur - (regex: ^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!\\-_]).{8,}$)", example = "Motdepasse2024@")
        @NotNull
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!\\-_]).{8,}$")
        String password
) {
}
