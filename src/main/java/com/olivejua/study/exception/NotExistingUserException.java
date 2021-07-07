package com.olivejua.study.exception;

public class NotExistingUserException extends RuntimeException {

    private static final String NOT_EXISTING_USER_MESSAGE = "존재하지 않는 유저입니다.";

    public NotExistingUserException() {
        super(NOT_EXISTING_USER_MESSAGE);
    }

    public NotExistingUserException(String message) {
        super(message);
    }
}
