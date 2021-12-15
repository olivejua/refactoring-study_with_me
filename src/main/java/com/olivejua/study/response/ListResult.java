package com.olivejua.study.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResult<T> extends SuccessResult {
    private List<T> content;

    public ListResult<T> createSuccessResult(List<T> content) {
        ListResult<T> result = new ListResult<>();
        result.success();
        result.content = content;

        return result;
    }
}
