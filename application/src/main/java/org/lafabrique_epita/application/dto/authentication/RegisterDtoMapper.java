package org.lafabrique_epita.application.dto.authentication;

import org.lafabrique_epita.domain.entities.UserEntity;

public class RegisterDtoMapper {

    private RegisterDtoMapper() {
    }

    public static UserEntity convertToUserEntity(RegisterDto registerDto) {
        UserEntity user = new UserEntity();
        user.setPassword(registerDto.password());
        user.setEmail(registerDto.email());
        user.setPseudo(registerDto.username());
        return user;
    }
}
