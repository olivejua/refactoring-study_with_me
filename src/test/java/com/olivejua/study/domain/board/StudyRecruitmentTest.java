package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudyRecruitmentTest {

    @Autowired
    EntityManager em;

    private User writer;
    private List<String> techStack;
    private Condition condition;

    @BeforeEach
    void setup() {
        writer = User.createUser("user1", "user1@gmail.com", Role.USER, "google");
        em.persist(writer);

        techStack = new ArrayList<>();
        techStack.add("tech1");
        techStack.add("tech2");
        techStack.add("tech3");
        techStack.add("tech4");

        condition = Condition.createCondition(
                "sample place1",
                LocalDateTime.of(2021, 6, 1, 0, 0),
                LocalDateTime.of(2021, 12, 31, 0, 0),
                10,
                "sample explanation1");
    }

    @Test
    @DisplayName("스터디 모집 - 게시글 작성")
    public void write() {
        //when
        StudyRecruitment post = StudyRecruitment.createPost(writer, "title1", techStack, condition);
        em.persist(post);
        post.getTechStack().forEach(em::persist);

        StudyRecruitment findPost = em.find(StudyRecruitment.class, post.getId());

        //then
        assertEquals(post.getId(), findPost.getId());
        assertEquals(post.getWriter().getId(), findPost.getWriter().getId());
        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getTechStack(), findPost.getTechStack());
        assertEquals(post.getCondition(), post.getCondition());
    }

    @Test
    @DisplayName("스터디 모집 - 게시글 수정")
    void edit() {
        //given
        StudyRecruitment post = StudyRecruitment.createPost(writer, "title1", techStack, condition);
        em.persist(post);
        post.getTechStack().forEach(em::persist);

        post = em.find(StudyRecruitment.class, post.getId());

        Condition changedCondition = Condition.createCondition(
                "sample place2",
                LocalDateTime.of(2021, 7, 1, 0, 0),
                LocalDateTime.of(2022, 1, 31, 0, 0),
                11,
                "sample explanation2");

        List<String> changedTechStack = new ArrayList<>();
        changedTechStack.add("tech5");
        changedTechStack.add("tech6");
        changedTechStack.add("tech7");
        changedTechStack.add("tech8");

        //when
        post.update("changedTitle", changedCondition, changedTechStack);
        post.getTechStack().forEach(em::persist);

        em.flush();
        em.clear();

        //then
        StudyRecruitment findPost = em.find(StudyRecruitment.class, post.getId());

        assertEquals(post.getId(), findPost.getId());
        assertEquals(post.getWriter().getId(), findPost.getWriter().getId());
        assertEquals(post.getTitle(), findPost.getTitle());
//        assertEquals(post.getTechStack(), findPost.getTechStack());
        assertEquals(post.getCondition(), post.getCondition());
    }
}