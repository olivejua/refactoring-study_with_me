package com.olivejua.study.exception.user;

public class NotFoundUserException extends RuntimeException {

    public NotFoundUserException() {
        super("User를 찾지 못했습니다.");
    }
}
