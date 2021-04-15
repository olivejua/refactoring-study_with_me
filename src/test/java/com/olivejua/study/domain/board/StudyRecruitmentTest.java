package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.sampleData.SampleLanguage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

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

    @Test
    @DisplayName("스터디 모집 - 게시글 수정")
    void edit() {
        //given
        StudyRecruitment post = createPost();
        Condition changedCondition = Condition.builder()
                .languages(LanguageTest.createLanguages(new String[]{"java", "spring", "jpa", "gcp", "mysql"}))
                .place("잠실")
                .startDate(LocalDateTime.of(2021, 5, 1, 0, 0))
                .endDate(LocalDateTime.of(2021, 8, 31, 0, 0))
                .capacity(5)
                .explanation("java 프로젝트 할 사람 모집합니다.")
                .build();

        //when
        post.edit("스터디 모집합니다-수정", changedCondition);

        //then
        StudyRecruitment findPost = em.find(StudyRecruitment.class, post.getId());

        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getCondition().getLanguages(), findPost.getCondition().getLanguages());
        assertEquals(post.getCondition().getPlace(), findPost.getCondition().getPlace());
        assertEquals(post.getCondition().getStartDate(), findPost.getCondition().getStartDate());
        assertEquals(post.getCondition().getEndDate(), findPost.getCondition().getEndDate());
        assertEquals(post.getCondition().getCapacity(), findPost.getCondition().getCapacity());
        assertEquals(post.getCondition().getExplanation(), findPost.getCondition().getExplanation());
    }

    private StudyRecruitment createPost() {
        StudyRecruitment post = StudyRecruitment.builder()
                .writer(createWriter())
                .title("자바 프로젝트 시작합니다")
                .condition(createCondition())
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

    private Condition createCondition() {
        Condition condition = Condition.builder()
                .languages(SampleLanguage.createList())
                .place("강남")
                .startDate(LocalDateTime.of(2021, 4, 7, 0, 0))
                .endDate(LocalDateTime.of(2021, 6, 7, 0, 0))
                .capacity(5)
                .explanation("java 프로젝트 할 사람 모집")
                .build();

        for (Language language : condition.getLanguages()) {
            em.persist(language);
        }

        return condition;
    }
}