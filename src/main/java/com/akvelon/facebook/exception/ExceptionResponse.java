package com.akvelon.facebook.exception;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ExceptionResponse {
    private final LocalDateTime timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;

    private ExceptionResponse(LocalDateTime timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public static ExceptionResponse of(Integer status, String error, String message, String path) {
        return new ExceptionResponse(LocalDateTime.now(), status, error, message, path);
    }
}