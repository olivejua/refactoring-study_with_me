package com.olivejua.study.web.dto.board.question;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private Long postId;
    private String title;
    private String writerName;
    private int viewCount;
    private int commentCount;
    private LocalDateTime createdDate;

    public PostListResponseDto(Long postId, String title, String writerName, int viewCount
            , int commentCount, LocalDateTime createdDate) {
        this.postId = postId;
        this.title = title;
        this.writerName = writerName;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
    }
}
