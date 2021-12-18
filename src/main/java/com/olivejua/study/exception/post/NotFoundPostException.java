package com.olivejua.study.exception.post;

import com.olivejua.study.exception.ApplicationException;
import com.olivejua.study.utils.ErrorCodes;
import org.springframework.http.HttpStatus;

public class NotFoundPostException extends ApplicationException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String CODE = ErrorCodes.Post.NOT_FOUND_POST_EXCEPTION;
    private static final String MESSAGE = "Post 정보를 찾을 수 없습니다";

    public NotFoundPostException() {
        super(HTTP_STATUS, CODE, MESSAGE);
    }

    public NotFoundPostException(Long postId) {
        super(HTTP_STATUS, CODE,
                String.format("%s. postId=%d", MESSAGE, postId));
    }
}
