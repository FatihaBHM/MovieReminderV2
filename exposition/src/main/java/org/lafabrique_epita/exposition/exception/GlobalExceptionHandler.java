package org.lafabrique_epita.exposition.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.lafabrique_epita.domain.exceptions.MovieReminderException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String STATUS = "status";
    private static final String ERROR_MESSAGE = "error_message";

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleException(DataIntegrityViolationException exception) {
        Map<String, ?> m = Map.of(STATUS, 400, ERROR_MESSAGE, exception.getMessage());
        try {
            String responseBody = mapper.writeValueAsString(m);
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(responseBody);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).build();
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        log.error("An error occurred", exception);
        return getStringResponseEntity(exception);
    }

    private ResponseEntity<String> getStringResponseEntity(Exception exception) {
        Map<String, ?> m = Map.of(STATUS, 500, ERROR_MESSAGE, exception.getMessage());
        try {
            String responseBody = mapper.writeValueAsString(m);
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(responseBody);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).build();
        }
    }

    @ExceptionHandler(MovieReminderException.class)
    public ResponseEntity<String> handleException(MovieReminderException exception) {
        Map<String, ?> m = Map.of(STATUS, exception.getHttpStatus().value(), ERROR_MESSAGE, exception.getMessage());
        try {
            String responseBody = mapper.writeValueAsString(m);
            return ResponseEntity.status(exception.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(responseBody);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).build();
        }
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleException(IllegalStateException exception) {
        return getStringResponseEntity(exception);
    }


    private Map<String, String> collectErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                String fieldName = fieldError.getField();

                Object target = exception.getBindingResult().getTarget();
                if (target != null) {
                    Field field = ReflectionUtils.findField(target.getClass(), fieldName);
                    if (field != null) {
                        JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                        if (annotation != null) {
                            fieldName = annotation.value(); // Utiliser la valeur de l'annotation JsonProperty
                        }
                    }
                }
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            }
        });
        return errors;
    }

    private String prepareResponse(Map<String, String> errorMessage) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(ERROR_MESSAGE, errorMessage);
        responseBody.put(STATUS, 400);

        try {
            return mapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException e) {
            return "Internal server error";
        }
    }
}
