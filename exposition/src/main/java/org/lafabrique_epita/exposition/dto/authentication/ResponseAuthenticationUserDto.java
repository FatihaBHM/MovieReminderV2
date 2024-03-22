package org.lafabrique_epita.exposition.dto.authentication;

public record ResponseAuthenticationUserDto(
        String username,
        String email
) {
}
