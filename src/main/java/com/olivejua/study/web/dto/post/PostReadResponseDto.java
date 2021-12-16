package com.olivejua.study.web.dto.post;

import lombok.Getter;

@Getter
public class PostReadResponseDto<T> {
    private T post;

    public PostReadResponseDto(T post) {
        this.post = post;
    }
}
