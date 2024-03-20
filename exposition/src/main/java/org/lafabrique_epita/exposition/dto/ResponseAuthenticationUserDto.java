package org.lafabrique_epita.exposition.dto;

public record ResponseAuthenticationUserDto(
        String username,
        String email
) {
}
