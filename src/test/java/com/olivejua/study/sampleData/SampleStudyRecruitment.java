package com.olivejua.study.sampleData;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.StudyRecruitment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SampleStudyRecruitment {
    public static StudyRecruitment create(User writer) {
        return StudyRecruitment.createPost(writer, "스터디 모집합니다.", createTechStack(), SampleCondition.create());
    }

    public static StudyRecruitment create(User writer, String[] techStack) {
        return StudyRecruitment.createPost(writer, "스터디 모집합니다.", createTechStack(techStack), SampleCondition.create());
    }

    public static StudyRecruitment create(User writer, String title, String[] techStack) {
        return StudyRecruitment.createPost(writer, title, createTechStack(techStack), SampleCondition.create());
    }

    public static List<StudyRecruitment> createList(List<User> writer, List<String> titles, List<String[]> techStacks) {
        List<StudyRecruitment> results = new ArrayList<>();

        for(int i=0; i<writer.size(); i++) {
            results.add(create(writer.get(i), titles.get(i), techStacks.get(i)));
        }

        return results;
    }

    public static List<StudyRecruitment> createList100(List<User> writer, List<String> titles, List<String[]> techStacks) {
        List<StudyRecruitment> results = new ArrayList<>();

        for(int i=0; i<writer.size()*20; i++) {
            int idx = i%5;
            results.add(create(writer.get(idx), titles.get(idx), techStacks.get(idx)));
        }

        return results;
    }

    static class SampleCondition {
        static Condition create() {
            return Condition.createCondition(
                    "강남",
                    LocalDate.of(2021, 4, 7),
                    LocalDate.of(2021, 6, 7),
                    5,
                    "java 프로젝트 할 사람 모집");
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

    public static List<String> createTechStack(String[] techStack) {
        return Arrays.stream(techStack)
                .collect(Collectors.toList());
    }
}
