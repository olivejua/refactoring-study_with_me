package com.olivejua.study.web.dto.board.question;

import com.olivejua.study.domain.board.Question;
import com.olivejua.study.web.dto.comment.CommentReadResponseDto;
import com.olivejua.study.web.dto.user.WriterReadDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostReadResponseDto {
    private String title;
    private WriterReadDto writer;
    private String content;
    private int viewCount;
    private List<CommentReadResponseDto> comments;
    private LocalDateTime createdDate;

    public PostReadResponseDto(Question entity) {
        this.title = entity.getTitle();
        this.writer = new WriterReadDto(entity.getWriter());
        this.content = entity.getContent();
        this.viewCount = entity.getViewCount();
        this.comments = entity.getComment().stream()
                .map(CommentReadResponseDto::new)
                .collect(Collectors.toList());
        this.createdDate = entity.getCreatedDate();
    }
}
