package com.olivejua.study.sampleData;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;

public class SampleQuestion {
    public static Question create(User writer) {
        return Question.builder()
                .writer(writer)
                .title("질문있습니다.")
                .content("보라돌이 뚜비 나나 뽀~↗")
                .build();
    }
}
