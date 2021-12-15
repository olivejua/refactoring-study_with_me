package com.olivejua.study.service.auth;

import com.olivejua.study.response.FailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseService {

    public FailResult getAuthorizationFailResult(String code, String msg) {
        return FailResult.createFailResult(code, msg);
    }
}
