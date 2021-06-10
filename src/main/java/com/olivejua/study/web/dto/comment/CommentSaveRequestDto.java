package com.olivejua.study.web.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private Long postId;
    private String content;

    public CommentSaveRequestDto(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
