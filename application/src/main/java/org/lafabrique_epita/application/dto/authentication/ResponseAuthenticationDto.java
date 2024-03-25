package org.lafabrique_epita.application.dto.authentication;

public record ResponseAuthenticationDto(
        String token,
        ResponseAuthenticationUserDto user
) {
}
