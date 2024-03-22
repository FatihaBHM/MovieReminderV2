package org.lafabrique_epita.exposition.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
//        System.out.println(exception.getMessage());
        Map<String, ?> m = Map.of("status", 400, "errorMessage", exception.getMessage());
        try {
            String responseBody = mapper.writeValueAsString(m);
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(responseBody);

        }catch (JsonProcessingException e) {
            String resp = new StringBuilder()
                    .append("{\n")
                    .append("    \"status\": 400,\n")
                    .append("    \"errorMessage\": \"")
                    .append(exception.getMessage())
                    .append("\"\n")
                    .append("}")
                    .toString();
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        Map<String, ?> m = Map.of("status", 500, "errorMessage", exception.getMessage());
        try {
            String responseBody = mapper.writeValueAsString(m);
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(responseBody);

        } catch (JsonProcessingException e) {
            String resp = new StringBuilder()
                    .append("{\n")
                    .append("    \"status\": 500,\n")
                    .append("    \"errorMessage\": \"Internal server error\"\n")
                    .append("}")
                    .toString();
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(resp);
        }
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleException(IllegalStateException exception) {
        Map<String, ?> m = Map.of("status", 500, "errorMessage", exception.getMessage());
        try {
            String responseBody = mapper.writeValueAsString(m);
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(responseBody);

        } catch (JsonProcessingException e) {
            String resp = new StringBuilder()
                    .append("{\n")
                    .append("    \"status\": 500,\n")
                    .append("    \"errorMessage\": \"Internal server error\"\n")
                    .append("}")
                    .toString();
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(resp);
        }
    }


    private Map<String, String> collectErrors(MethodArgumentNotValidException exception) {
        // TODO: si variable idTmdb retourner id_tmdb
        Map<String, String> err = new HashMap<>();
         for (ObjectError error : exception.getBindingResult().getAllErrors()) {
             err.put(((FieldError) error).getField(), error.getDefaultMessage());
         }

        return err;
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
