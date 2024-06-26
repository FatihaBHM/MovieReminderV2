package org.lafabrique_epita.exposition.api.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.lafabrique_epita.application.dto.authentication.AuthenticationDto;
import org.lafabrique_epita.application.dto.authentication.RegisterDto;
import org.lafabrique_epita.application.dto.authentication.ResponseAuthenticationDto;
import org.lafabrique_epita.application.dto.authentication.ResponseAuthenticationUserDto;
import org.lafabrique_epita.application.service.user.UserServicePort;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.exceptions.UserException;
import org.lafabrique_epita.exposition.api.ApiControllerBase;
import org.lafabrique_epita.exposition.configuration.JwtService;
import org.lafabrique_epita.exposition.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "User", description = "L'API utilisateur")
@RestController
public class UserController extends ApiControllerBase {

    private final UserServicePort userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final ObjectMapper mapper;

    public UserController(UserServicePort userService, AuthenticationManager authenticationManager, JwtService jwtService, ObjectMapper mapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }


    @Operation(summary = "Enregistrez un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur enregistré",
                    content = @Content(schema = @Schema(implementation = Long.class),
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "1")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Mauvaise demande",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class),
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"status\": 400, \"error_message\": {\"email\": \"ne doit pas être vide\"} }"))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<Long> save(@Valid @RequestBody RegisterDto registerDto) throws UserException {
        Long id = userService.save(registerDto);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Connectez-vous à l'application")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur connecté",
                    content = @Content(schema = @Schema(implementation = ResponseAuthenticationDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Non autorisé",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody AuthenticationDto authenticationDto) {
        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.password())
            );
            if (authenticate.isAuthenticated()) {
                UserEntity userEntity = (UserEntity) authenticate.getPrincipal();
                ResponseAuthenticationUserDto user = new ResponseAuthenticationUserDto(userEntity.getPseudo(), userEntity.getEmail());
                ResponseAuthenticationDto response = new ResponseAuthenticationDto(jwtService.generateToken(userEntity.getEmail()).get("bearer"), user);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (BadCredentialsException e) {
            return errors("Les informations d'identification sont incorrectes");
        }
        return errors("Erreur inconnue");
    }

    private ResponseEntity<Object> errors(String message) {
        Map<String, ?> m = Map.of("status", HttpStatus.UNAUTHORIZED.value(), "error_message", (Object) message);
        try {
            String responseBody = mapper.writeValueAsString(m);
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);

        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
