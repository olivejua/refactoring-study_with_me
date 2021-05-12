package com.olivejua.study.sampleData;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;

import java.util.List;

public class SampleQuestion {
    public static Question create(User writer) {
        return Question.savePost(
                writer,
                "질문있습니다.",
                "보라돌이 뚜비 나나 뽀~↗"
        );
    }

    public static PostSaveRequestDto createSaveRequest(User writer) {
        return PostSaveRequestDto.builder()
                .title("JPA 관련 질문입니다.")
                .content("JPA에서 쿼리가 나가는 시점은?")
                .build();
    }
}
