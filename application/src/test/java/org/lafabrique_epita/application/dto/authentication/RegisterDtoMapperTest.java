package org.lafabrique_epita.application.dto.authentication;


import org.junit.jupiter.api.Test;
import org.lafabrique_epita.domain.entities.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RegisterDtoMapperTest {

    // Uncomment the following lines
    @Test
    void toEntity() {
        // Arrange
        RegisterDto registerDto = new RegisterDto("pseudo", "sam@sam.fr", "samsamsam");

        UserEntity userEntity = RegisterDtoMapper.convertToUserEntity(registerDto);

        // Assert
        assertEquals(registerDto.username(), userEntity.getPseudo());
        assertEquals(registerDto.email(), userEntity.getEmail());
        assertEquals(registerDto.password(), userEntity.getPassword());

    }

}
