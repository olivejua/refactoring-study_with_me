package com.olivejua.study.web.dto.post;

import lombok.Getter;

@Getter
public class PostResponseDto<T> {
    private T post;

    public PostResponseDto(T post) {
        this.post = post;
    }
}
