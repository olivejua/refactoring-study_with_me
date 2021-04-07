package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StudyRecruitmentTest {

    @Test
    @DisplayName("스터디 모집 - 게시글 작성")
    public void write() {
//        //given
//        User user = createUser();
//
//        //when
//        StudyRecruitment post = createPost();
//
//        //then
//        assertEquals(createLanguages().toArray(), post.getCondition().getLanguages().toArray());
    }

    @Test
    @DisplayName("스터디 모집 - 게시글 수정")
    void edit() {
        //when
        User user = createUser();


    }

    //TODO Language와 StudyRecruitment 생성자에서 둘 다 파라미터로 넘기는데 어떻게 해결할 지 생각해보기
//    private static StudyRecruitment createPost() {
//        return StudyRecruitment.builder()
//                .condition(createCondition())
//                .build();
//    }

//    private static Condition createCondition() {
//        return Condition.builder()
//                .languages(createLanguages())
//                .place("강남")
//                .startDate(LocalDateTime.of(2021, 4, 7, 0, 0))
//                .endDate(LocalDateTime.of(2021, 6, 7, 0, 0))
//                .capacity(5)
//                .explanation("java 프로젝트 할 사람 모집")
//                .build();
//    }

//    private static List<Language> createLanguages() {
//        String[] words = {"java", "spring", "jpa", "aws", "h2"};
//
//        return Arrays.stream(words)
//                .map(Language::new)
//                .collect(Collectors.toList());
//    }

    static User createUser() {
        return User.builder()
                .id(1L)
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();
    }
}