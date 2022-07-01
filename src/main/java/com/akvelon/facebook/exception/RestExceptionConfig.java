package com.akvelon.facebook.exception;

import com.akvelon.facebook.exception.ExceptionResponse;
import com.akvelon.facebook.exception.RestException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
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
}
