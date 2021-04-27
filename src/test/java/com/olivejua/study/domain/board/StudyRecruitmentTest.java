package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.sampleData.SampleStudyRecruitment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StudyRecruitmentTest {

    @Autowired
    TestEntityManager em;

    private User writer;

    @BeforeEach
    void setup() {
        writer = User.builder()
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        em.persist(writer);
    }

    @Test
    @DisplayName("스터디 모집 - 게시글 작성")
    public void write() {
        //when
        StudyRecruitment post = SampleStudyRecruitment.create(writer);
        em.persist(post);

        StudyRecruitment findPost = em.find(StudyRecruitment.class, post.getId());

        //then
        assertEquals(post.getWriter().getId(), findPost.getWriter().getId());
        assertEquals(post.getTitle(), findPost.getTitle());
    }

    @Test
    @DisplayName("스터디 모집 - 게시글 수정")
    void edit() {
        //given
        StudyRecruitment post = SampleStudyRecruitment.create(writer);
        em.persist(post);
        post.getTechStack().forEach(em::persist);

        //when
        Condition changedCondition = Condition.builder()
                .place("사당")
                .startDate(LocalDateTime.of(2021, 4, 7, 0, 0))
                .endDate(LocalDateTime.of(2021, 10, 7, 0, 0))
                .capacity(10)
                .explanation("java 프로젝트 할 사람 모집 - 수정")
                .build();

        List<String> changedTechStack = new ArrayList<>();
        changedTechStack.add("node");
        changedTechStack.add("typescript");
        changedTechStack.add("react");
        changedTechStack.add("gcp");

        post.edit("스터디 모집합니다-수정", changedCondition, changedTechStack);

        //then
        StudyRecruitment findPost = em.find(StudyRecruitment.class, post.getId());

        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getCondition().getPlace(), findPost.getCondition().getPlace());
        assertEquals(post.getCondition().getStartDate(), findPost.getCondition().getStartDate());
        assertEquals(post.getCondition().getEndDate(), findPost.getCondition().getEndDate());
        assertEquals(post.getCondition().getCapacity(), findPost.getCondition().getCapacity());
        assertEquals(post.getCondition().getExplanation(), findPost.getCondition().getExplanation());
    }
}