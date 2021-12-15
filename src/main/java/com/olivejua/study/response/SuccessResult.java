package com.olivejua.study.response;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
public class SuccessResult extends CommonResult {
    public static SuccessResult createSuccessResult() {
        SuccessResult result = new SuccessResult();
        result.success();

        return result;
    }
}
