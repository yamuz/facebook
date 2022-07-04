package com.akvelon.facebook.exception;

import com.akvelon.facebook.exception.validation.ValidationErrorDto;
import com.akvelon.facebook.exception.validation.ValidationErrorsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@AllArgsConstructor
public class RestExceptionConfig {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ExceptionResponse> handleException(RestException e, HttpServletRequest request) {
        HttpStatus httpStatus = Optional.ofNullable(e.getClass())
                .map(status -> status.getAnnotation(ResponseStatus.class))
                .map(ResponseStatus::value)
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);


        ExceptionResponse error = ExceptionResponse.of(httpStatus.value(), e.getMessageCode(), e.getSysMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorsDto> handleException(MethodArgumentNotValidException exception) {
        List<ValidationErrorDto> errors = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            String fieldName = null;
            String objectName = error.getObjectName();
            if (error instanceof FieldError) {
                fieldName = ((FieldError) error).getField();
            }

            ValidationErrorDto validationError = ValidationErrorDto.builder()
                    .message(errorMessage)
                    .field(fieldName)
                    .object(objectName)
                    .build();

            errors.add(validationError);
        });

        return ResponseEntity.ok(ValidationErrorsDto.builder()
                .errors(errors)
                .build());
    }
}
