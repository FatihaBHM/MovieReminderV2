package org.lafabrique_epita.exposition.dto.authentication;

public record ResponseAuthenticationDto(
        String token,
        ResponseAuthenticationUserDto user
) {
}
