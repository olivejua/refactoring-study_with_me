package com.olivejua.study.sampleData;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.Language;
import com.olivejua.study.domain.board.StudyRecruitment;

import java.time.LocalDateTime;
import java.util.List;

public class SampleStudyRecruitment {

    public static StudyRecruitment create(User writer, List<Language> languages) {
        return StudyRecruitment.builder()
                .writer(writer)
                .title("스터디 모집합니다.")
                .condition(SampleCondition.create(languages))
                .build();
    }

    static class SampleCondition {
        static Condition create(List<Language> languages) {
            return Condition.builder()
                    .languages(languages)
                    .place("강남")
                    .startDate(LocalDateTime.of(2021, 4, 7, 0, 0))
                    .endDate(LocalDateTime.of(2021, 6, 7, 0, 0))
                    .capacity(5)
                    .explanation("java 프로젝트 할 사람 모집")
                    .build();
        }
    }
}
