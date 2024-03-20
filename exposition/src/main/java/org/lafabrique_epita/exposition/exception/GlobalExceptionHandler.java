package org.lafabrique_epita.exposition.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleException(DataIntegrityViolationException exception) {
        System.out.println(exception.getMessage());
        Map<String, ?> m = Map.of("status", 400, "errorMessage", "L'utilisateur existe déjà");
        try {
            String responseBody = mapper.writeValueAsString(m);
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(responseBody);

        }catch (JsonProcessingException e) {
            String resp = """
{
    "status": 400,
    "errorMessage": "L'utilisateur existe déjà"
}""";
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(resp);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException exception) {

        Map<String, String> errors = collectErrors(exception);

        String jsonErrors = prepareResponse(errors);
        if (jsonErrors.equals("Internal server error")) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(jsonErrors);
        }
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(jsonErrors);
    }


    private Map<String, String> collectErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        DefaultMessageSourceResolvable::getDefaultMessage));
    }

    private String prepareResponse(Map<String, String> errorMessage) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("errorMessage", errorMessage);
        responseBody.put("status", 400);

        try {
            return mapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException e) {
            return "Internal server error";
        }
    }
}
