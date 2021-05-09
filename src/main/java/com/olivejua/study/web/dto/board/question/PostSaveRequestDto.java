package com.olivejua.study.web.dto.board.question;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSaveRequestDto {
    private String title;
    private String content;

    @Builder
    public PostSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
