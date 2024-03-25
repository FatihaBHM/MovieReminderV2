package org.lafabrique_epita.application.dto.authentication;

public record ResponseAuthenticationUserDto(
        String username,
        String email
) {
}
