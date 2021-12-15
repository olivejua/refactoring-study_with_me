package com.olivejua.study.exception.studyRecruitment;

import com.olivejua.study.exception.ApplicationException;
import com.olivejua.study.utils.ErrorCodes;
import org.springframework.http.HttpStatus;

public class NotFoundStudyRecruitmentPost extends ApplicationException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String CODE = ErrorCodes.StudyRecruitment.NOT_FOUND_STUDY_RECRUITMENT_EXCEPTION;
    private static final String MESSAGE = "Post 정보를 찾을 수 없습니다";

    public NotFoundStudyRecruitmentPost() {
        super(HTTP_STATUS, CODE, MESSAGE);
    }

    public NotFoundStudyRecruitmentPost(Long postId) {
        super(HTTP_STATUS, CODE,
                String.format("%s. postId=%d", MESSAGE, postId));
    }
}
