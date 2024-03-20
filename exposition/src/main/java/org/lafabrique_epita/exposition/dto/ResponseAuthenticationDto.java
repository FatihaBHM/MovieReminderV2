package org.lafabrique_epita.exposition.dto;

public record ResponseAuthenticationDto(
        String token,
        ResponseAuthenticationUserDto user
) {
}
