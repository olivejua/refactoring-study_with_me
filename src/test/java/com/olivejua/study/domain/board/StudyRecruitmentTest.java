package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StudyRecruitmentTest {

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("스터디 모집 - 게시글 작성")
    public void write() {
        //when
        StudyRecruitment post = createPost();

        StudyRecruitment findPost = em.find(StudyRecruitment.class, post.getId());

        //then
        assertEquals(post.getWriter().getId(), findPost.getWriter().getId());
        assertEquals(post.getTitle(), findPost.getTitle());
    }

//    @Test
//    @DisplayName("스터디 모집 - 게시글 수정")
//    void edit() {
//        //when
//        User user = createUser();
//
//
//    }

    private StudyRecruitment createPost() {
        StudyRecruitment post = StudyRecruitment.builder()
                .writer(createWriter())
                .title("자바 프로젝트 시작합니다")
                .condition(ConditionTest.createCondition())
                .build();

        em.persist(post);
        return post;
    }

    private User createWriter() {
        User user = User.builder()
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        em.persist(user);
        return user;
    }
}