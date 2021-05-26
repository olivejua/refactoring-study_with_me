package com.olivejua.study.web.dto.board.place;

import java.time.LocalDateTime;

public class PostListResponseDto {
    private Long postId;
    private String writerName;
    private String title;
    private String thumbnailPath;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private LocalDateTime createdDate;

    public PostListResponseDto(Long postId, String writerName, String title,
                               String thumbnailPath, int likeCount, int dislikeCount,
                               int viewCount, LocalDateTime createdDate) {
        this.postId = postId;
        this.writerName = writerName;
        this.title = title;
        this.thumbnailPath = thumbnailPath;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
    }
}
