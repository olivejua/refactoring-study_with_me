package com.olivejua.study.exception;

public class NotExistsUserException extends RuntimeException {

    private static final String NOT_EXISTING_USER_MESSAGE = "존재하지 않는 유저입니다.";

    public NotExistsUserException() {
        super(NOT_EXISTING_USER_MESSAGE);
    }

    public NotExistsUserException(String message) {
        super(message);
    }
}
