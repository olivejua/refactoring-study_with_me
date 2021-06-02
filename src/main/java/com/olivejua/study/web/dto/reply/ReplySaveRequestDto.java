package com.olivejua.study.web.dto.reply;

import lombok.Getter;

@Getter
public class ReplySaveRequestDto {

    private Long commentId;
    private String content;

    public ReplySaveRequestDto(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
