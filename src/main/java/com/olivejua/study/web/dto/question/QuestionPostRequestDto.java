package com.olivejua.study.web.dto.question;

import com.olivejua.study.domain.board.Question;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionPostRequestDto {
    private String title;
    private String content;

    @Builder
    public QuestionPostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
