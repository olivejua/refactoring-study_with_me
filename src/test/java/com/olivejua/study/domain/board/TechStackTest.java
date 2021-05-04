package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.sampleData.SampleStudyRecruitment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TechStackTest {

    @Test
    @DisplayName("언어목록이 모두 같아야 true")
    public void equalsTest_success() {
        List<TechStack> techStack1 = createTechStack(new String[] {"java", "spring", "jpa", "aws", "h2"});
        List<TechStack> techStack2 = createTechStack(new String[] {"java", "spring", "jpa", "aws", "h2"});

        assertThat(techStack1.equals(techStack2)).isTrue();
    }

    @Test
    @DisplayName("언어목록이 하나라도 다르면 false")
    public void equalsTest_fail() {
        List<TechStack> techStack1 = createTechStack(new String[] {"java", "spring", "jpa", "aws", "h2"});
        List<TechStack> techStack2 = createTechStack(new String[] {"java", "spring", "jpa", "gcp", "h2"});

        assertThat(techStack1.equals(techStack2)).isFalse();
    }

    public static List<TechStack> createTechStack(String[] words) {
        User writer = User.builder()
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        Condition condition = Condition.builder()
                .place("강남")
                .startDate(LocalDateTime.of(2021, 4, 7, 0, 0))
                .endDate(LocalDateTime.of(2021, 6, 7, 0, 0))
                .capacity(5)
                .explanation("java 프로젝트 할 사람 모집")
                .build();

        StudyRecruitment post = StudyRecruitment.savePost(writer, "스터디 모집합니다.", Arrays.asList(words), condition);

        return post.getTechStack();
    }
}
