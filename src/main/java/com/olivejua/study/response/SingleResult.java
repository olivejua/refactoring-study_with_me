package com.olivejua.study.response;

public class SingleResult<T> extends SuccessResult {
    private T content;

    public SingleResult<T> createSuccessResult(T content) {
        SingleResult<T> result = new SingleResult<>();
        result.success();
        result.content = content;

        return result;
    }
}