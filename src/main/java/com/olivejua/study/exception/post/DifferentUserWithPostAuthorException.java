package com.olivejua.study.exception.post;

import com.olivejua.study.exception.ApplicationException;
import com.olivejua.study.utils.ErrorCodes;
import org.springframework.http.HttpStatus;

public class DifferentUserWithPostAuthorException extends ApplicationException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String CODE = ErrorCodes.Post.DIFFERENT_USER_WITH_POST_AUTHOR_EXCEPTION;
    private static final String MESSAGE = "게시글 작성자와 로그인유저가 일치하지 않습니다";

    public DifferentUserWithPostAuthorException() {
        super(HTTP_STATUS, CODE, MESSAGE);
    }
}
