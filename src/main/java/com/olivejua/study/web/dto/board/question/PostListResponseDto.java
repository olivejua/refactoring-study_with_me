package com.olivejua.study.web.dto.board.question;

import com.olivejua.study.domain.board.Question;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private Long postId;
    private String writerName;
    private String title;
    private int viewCount;
    private int commentCount;
    private LocalDateTime createdDate;

    public PostListResponseDto(Question entity) {
        this.postId = entity.getId();
        this.writerName = entity.getWriter().getName();
        this.title = entity.getTitle();
        this.viewCount = entity.getViewCount();
        this.commentCount = entity.getComment().size();
        this.createdDate = entity.getCreatedDate();
    }
}
