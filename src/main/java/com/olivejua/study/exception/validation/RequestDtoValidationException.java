package com.olivejua.study.exception.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

public class RequestDtoValidationException extends IllegalArgumentException {

    public RequestDtoValidationException(Errors errors) {
        super(getMessage(errors));
    }

    private static String getMessage(Errors errors) {
        StringBuilder result = new StringBuilder();
        result.append("[");

        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            result.append(String.format("{RejectField: %s, Value: %s, Message: %s}, ",
                    fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
        }

        result.append("]");

        return result.toString();
    }
}
