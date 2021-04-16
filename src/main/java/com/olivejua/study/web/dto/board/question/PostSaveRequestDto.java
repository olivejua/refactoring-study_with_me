package com.olivejua.study.web.dto.board.question;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSaveRequestDto {
    private String title;
    private String content;

    @Builder
    public PostSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    public Question toEntity(User writer) {
//        return Question.builder()
//                .writer(writer)
//                .title(title)
//                .content(content)
//                .build();
//    }
}
