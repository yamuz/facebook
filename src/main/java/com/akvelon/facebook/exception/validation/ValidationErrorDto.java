package com.akvelon.facebook.exception.validation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationErrorDto {

    private String field;
    private String object;
    private String message;
}
