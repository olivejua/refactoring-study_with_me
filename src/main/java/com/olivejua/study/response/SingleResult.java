package com.olivejua.study.response;

import lombok.Getter;

@Getter
public class SingleResult extends SuccessResult {
    private Object content;

    public static SingleResult createSuccessResult(Object content) {
        SingleResult result = new SingleResult();
        result.success();
        result.content = content;

        return result;
    }
}