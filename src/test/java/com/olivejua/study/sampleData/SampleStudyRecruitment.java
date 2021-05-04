package com.olivejua.study.sampleData;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.TechStack;
import com.olivejua.study.domain.board.StudyRecruitment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SampleStudyRecruitment {
    public static StudyRecruitment create(User writer) {
        return StudyRecruitment.savePost(writer, "스터디 모집합니다.", createTechStack(), SampleCondition.create());
    }

    static class SampleCondition {
        static Condition create() {
            return Condition.builder()
                    .place("강남")
                    .startDate(LocalDateTime.of(2021, 4, 7, 0, 0))
                    .endDate(LocalDateTime.of(2021, 6, 7, 0, 0))
                    .capacity(5)
                    .explanation("java 프로젝트 할 사람 모집")
                    .build();
        }
    }

    public static List<String> createTechStack() {
        List<String> techStack = new ArrayList<>();
        techStack.add("java");
        techStack.add("spring");
        techStack.add("jpa");
        techStack.add("gcp");
        techStack.add("mysql");
        return techStack;
    }
}
