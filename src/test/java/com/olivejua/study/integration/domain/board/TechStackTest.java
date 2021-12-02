package com.olivejua.study.integration.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.domain.board.TechStack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
        User writer = User.createUser(
                "김슬기",
                "tmfrl4710@gmail.com",
                Role.GUEST,
                "google"
        );

        Condition condition = Condition.createCondition(
                "강남",
                LocalDateTime.of(2021, 4, 7, 0, 0),
                LocalDateTime.of(2021, 6, 7, 0, 0),
                5,
                "java 프로젝트 할 사람 모집");

        StudyRecruitment post = StudyRecruitment.createPost(writer, "스터디 모집합니다.", Arrays.asList(words), condition);

        return post.getTechStack();
    }
}
