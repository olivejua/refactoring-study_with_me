package com.olivejua.study.exception.user;

import com.olivejua.study.exception.ApplicationException;
import com.olivejua.study.utils.ErrorCodes;
import org.springframework.http.HttpStatus;

public class NotFoundUserException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String CODE = ErrorCodes.User.NOT_FOUND_USER_EXCEPTION;
    private static final String MESSAGE = "User 정보를 찾을 수 없습니다";

    public NotFoundUserException() {
        super(HTTP_STATUS, CODE, MESSAGE);
    }

    public NotFoundUserException(Long userId) {
        super(HTTP_STATUS,
                CODE,
                String.format("%s. userId=%d", MESSAGE, userId));
    }
}
