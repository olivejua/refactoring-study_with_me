package com.olivejua.study.web.advice;

import com.olivejua.study.exception.ApplicationException;
import com.olivejua.study.response.FailResult;
import com.olivejua.study.utils.ErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApplicationException.class})
    protected ResponseEntity<FailResult> handleApplicationException(ApplicationException exception) {
        FailResult result = FailResult.createFailResult(exception.CODE, exception.MESSAGE);

        return ResponseEntity
                .status(exception.HTTP_STATUS)
                .body(result);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        FailResult failResult = FailResult.createFailResult(
                ErrorCodes.Global.CONSTRAINT_VIOLATION_EXCEPTION, makeFieldErrorMessages(ex.getFieldErrors()));

        return ResponseEntity.badRequest()
                .body(failResult);
    }

    private String makeFieldErrorMessages(List<FieldError> fieldErrors) {
        StringBuilder messages = new StringBuilder();
        messages.append("fieldErrors: [");

        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String objectName = fieldError.getObjectName();
            String defaultMessage = fieldError.getDefaultMessage();

            messages.append(String.format("{%s.%s: %s}, ", objectName, field, defaultMessage));
        }
        messages.append("]");

        return messages.toString();
    }
}
