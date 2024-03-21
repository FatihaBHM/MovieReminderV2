package org.lafabrique_epita.exposition.api.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.exposition.configuration.JwtService;

import org.lafabrique_epita.exposition.dto.authentication.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.lafabrique_epita.application.service.user.UserServiceImpl;

import java.util.Map;

@RestController
public class UserController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final ObjectMapper mapper;

    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager, JwtService jwtService, ObjectMapper mapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Register",
            description = "Register to the application",
            responses = {
                @ApiResponse(responseCode = "200", description = "Register successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
            }
    )
    @PostMapping("/register")
    public ResponseEntity<Long> save(@Valid @RequestBody RegisterDto registerDto) {
        UserEntity user = RegisterDtoMapper.convertToUserEntity(registerDto);
        Long id = userService.save(user);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Login",
            description = "Login to the application",
            responses = {
                @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseAuthenticationDto.class))),
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDto authenticationDto) {
//        System.out.println(authenticationDto);
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.password())
        );
//        System.out.println(authenticate.isAuthenticated());
        if (authenticate.isAuthenticated()) {
            UserEntity userEntity = (UserEntity) authenticate.getPrincipal();
            ResponseAuthenticationUserDto user = new ResponseAuthenticationUserDto(userEntity.getPseudo(), userEntity.getEmail());
            ResponseAuthenticationDto response = new ResponseAuthenticationDto(jwtService.generateToken(userEntity.getEmail()).get("bearer"),user);
            return new ResponseEntity<>(response, HttpStatus.OK);
//            return ResponseEntity.ok(response);
        }
//        System.out.println("coucou, je suis sam");
        return errors(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    private ResponseEntity<?> errors(HttpStatus status, Object errorMessage) {
        Map<String, ?> m = Map.of("status", status, "errorMessage", errorMessage);
        try {
            String responseBody = mapper.writeValueAsString(m);
            return new ResponseEntity<>(responseBody, status);

        }catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
