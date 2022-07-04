package com.akvelon.facebook.exception.validation;

import lombok.Builder;

import java.util.List;

@Builder
public class ValidationErrorsDto {
    private List<ValidationErrorDto> errors;
}
