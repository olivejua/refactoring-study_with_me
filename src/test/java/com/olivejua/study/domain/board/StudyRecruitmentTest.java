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
        //when
        StudyRecruitment post = createPost();

        //then
        assertEquals(1L, post.getUser().getId());
        assertEquals("자바 프로젝트 시작합니다", post.getTitle());
    }

//    @Test
//    @DisplayName("스터디 모집 - 게시글 수정")
//    void edit() {
//        //when
//        User user = createUser();
//
//
//    }

    private static StudyRecruitment createPost() {
        return StudyRecruitment.builder()
                .user(createUser())
                .title("자바 프로젝트 시작합니다")
                .condition(ConditionTest.createCondition())
                .build();
    }

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