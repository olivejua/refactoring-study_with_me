package com.olivejua.study.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {
    public final HttpStatus HTTP_STATUS;
    public final String CODE;
    public final String MESSAGE;

    public ApplicationException(HttpStatus httpStatus, String code, String message) {
        super(message);
        HTTP_STATUS = httpStatus;
        CODE = code;
        MESSAGE = message;
    }
}
