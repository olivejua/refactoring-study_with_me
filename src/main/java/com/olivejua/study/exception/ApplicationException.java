package com.olivejua.study.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {
    private final HttpStatus HTTP_STATUS;
    private final String CODE;
    private final String MESSAGE;

    public ApplicationException(HttpStatus httpStatus, String code, String message) {
        super(message);
        HTTP_STATUS = httpStatus;
        CODE = code;
        MESSAGE = message;
    }
}
