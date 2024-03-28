package org.lafabrique_epita.application.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthenticationDto(


        @Schema(description = "L'adresse email de l'utilisateur", example = "utilisateur@gmail.fr")
        @Email (message = "L'adresse email doit être valide")
        @NotNull (message = "L'adresse email ne doit pas être nulle")
        @NotBlank (message = "L'adresse email ne doit pas être vide")
        String email,

        @Schema(description = "Le mot de passe de l'utilisateur - (regex: ^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!\\-_]).{8,}$)", example = "Motdepasse2024@")
        @NotNull(message = "Le mot de passe ne doit pas être nul")
        @NotBlank(message = "Le mot de passe ne doit pas être vide")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!\\-_]).{8,}$", message = "Le mot de passe doit contenir au moins une lettre minuscule, une lettre majuscule, un chiffre et un caractère spécial")
        String password
) {
}
