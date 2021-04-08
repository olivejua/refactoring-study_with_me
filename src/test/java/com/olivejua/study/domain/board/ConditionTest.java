package com.olivejua.study.domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConditionTest {

    public static Condition createCondition() {
        return Condition.builder()
                .languages(LanguageTest.createLanguages(new String[] {"java", "spring", "jpa", "aws", "h2"}))
                .place("강남")
                .startDate(LocalDateTime.of(2021, 4, 7, 0, 0))
                .endDate(LocalDateTime.of(2021, 6, 7, 0, 0))
                .capacity(5)
                .explanation("java 프로젝트 할 사람 모집")
                .build();
    }
}