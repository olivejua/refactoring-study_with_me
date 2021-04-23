package com.olivejua.study.web.dto.question;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    String title;
    String content;

    @Builder
    public PostUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
