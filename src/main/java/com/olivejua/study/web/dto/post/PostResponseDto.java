package com.olivejua.study.web.dto.post;

public class PostResponseDto<T> {
    private T post;

    public PostResponseDto(T post) {
        this.post = post;
    }
}
