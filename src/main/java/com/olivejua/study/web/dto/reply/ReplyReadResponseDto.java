package com.olivejua.study.web.dto.reply;

import com.olivejua.study.domain.Reply;
import com.olivejua.study.web.dto.user.WriterReadDto;
import lombok.Getter;

@Getter
public class ReplyReadResponseDto {
    private WriterReadDto writer;
    private String content;

    public ReplyReadResponseDto(Reply entity) {
        this.writer = new WriterReadDto(entity.getWriter());
        this.content = entity.getContent();
    }
}
