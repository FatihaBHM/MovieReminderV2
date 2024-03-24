package org.lafabrique_epita.exposition.api.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lafabrique_epita.application.service.user.UserServiceAdapter;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.exceptions.UserException;
import org.lafabrique_epita.exposition.configuration.JwtService;
import org.lafabrique_epita.exposition.dto.authentication.RegisterDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserServiceAdapter userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController(userService, authenticationManager, jwtService, mapper);
    }

    @Test
    public void testSave() throws UserException {
        // Arrange
        RegisterDto registerDto = new RegisterDto("pseudo", "utilisateur@gmail.fr", "Motdepasse2024@");

        UserEntity userEntity = new UserEntity();
        userEntity.setPseudo("pseudo");
        userEntity.setEmail("utilisateur@gmail.fr");
        userEntity.setPassword("Motdepasse2024@");

        when(userService.save(any(UserEntity.class))).thenReturn(1L);

        // Act
        // Uncomment the following line
        ResponseEntity<Long> response = userController.save(registerDto);

        // Assert
        // Uncomment the following lines
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }


    @Test
    void testSaveThrowsException() throws UserException {
        // Arrange
        RegisterDto registerDto = new RegisterDto("pseudo", "utilisateur@gmail.fr", "Motdepasse2024@");

        UserEntity userEntity = new UserEntity();
        userEntity.setPseudo("pseudo");
        userEntity.setEmail("utilisateur@gmail.fr");
        userEntity.setPassword("Motdepasse2024@");

        when(userService.save(any(UserEntity.class))).thenThrow(new UserException("Email already exists", HttpStatus.UNAUTHORIZED));

        // Act and Assert
        Exception exception = assertThrows(UserException.class, () -> {
            userController.save(registerDto);
        });

        String expectedMessage = "Email already exists";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
