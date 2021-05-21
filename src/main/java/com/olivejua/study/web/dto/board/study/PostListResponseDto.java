package com.olivejua.study.web.dto.board.study;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostListResponseDto {
    Long postId;
    String title;
    String writerName;
    int viewCount;
    int commentCount;

    public PostListResponseDto(Long postId, String title, String writerName,
                               int viewCount, int commentCount) {
        this.postId = postId;
        this.title = title;
        this.writerName = writerName;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
    }
}
