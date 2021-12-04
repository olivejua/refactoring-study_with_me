package com.olivejua.study.web.dto.comment;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.web.dto.reply.ReplyReadResponseDto;
import com.olivejua.study.web.dto.user.WriterReadDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentReadResponseDto {
    private String content;
    private WriterReadDto writer;
    private List<ReplyReadResponseDto> replies;

    public CommentReadResponseDto(Comment entity) {
        this.content = entity.getContent();
        this.writer = new WriterReadDto(entity.getAuthor());
        this.replies = entity.getReplies().stream()
                .map(ReplyReadResponseDto::new)
                .collect(Collectors.toList());
    }
}
