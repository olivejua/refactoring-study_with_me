package com.olivejua.study.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class FailResult extends CommonResult {
    private String code;
    private String message;

    public static FailResult createFailResult(String code, String message) {
        FailResult result = new FailResult();
        result.fail(code, message);

        return result;
    }

    protected void fail(String code, String message) {
        super.fail();
        this.code = code;
        this.message = message;
    }
}
