package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    EntityManager em;

    //TODO create 엔티티 메서드 리팩토링 해야함. 도메인에 아예 넣는건 어떨지 생각해보자.
    @Test
    @DisplayName("댓글[스터디 모집] 작성")
    public void writeComment_study() throws Exception {
        //given
        StudyRecruitment post = createPost();

        //when
        Comment comment = createComment(post);

        System.out.println("comment.getId() = " + comment.getId());

        //then
        assertEquals(1L, comment.getId());
        assertEquals(post.getId(), comment.getBoard().getId());
        assertEquals(2L, comment.getWriter().getId());
        assertEquals("참여하고 싶습니다", comment.getContent());
    }

    @Disabled
    @Test
    @DisplayName("댓글[스터디 모집] 수정")
    void edit() {
        //given
        StudyRecruitment post = createPost();
        Comment comment = createComment(post);

        //when
        comment.edit("저도 참여하고 싶습니다");

        //then
        assertEquals(1L, comment.getId());
        assertEquals(post.getId(), comment.getBoard().getId());
        assertEquals(2L, comment.getWriter().getId());
        assertEquals("저도 참여하고 싶습니다", comment.getContent());
    }

    private Comment createComment(Board post) {
        return Comment.builder()
                .board(post)
                .writer(createCommentWriter())
                .content("참여하고 싶습니다")
                .build();
    }

    private User createCommentWriter() {
        return User.builder()
                .id(2L)
                .name("dreaming octopus")
                .email("dreamingoctopus@gmail.com")
                .role(Role.USER)
                .socialCode("google")
                .build();
    }

    private StudyRecruitment createPost() {
        StudyRecruitment post = StudyRecruitment.builder()
                .user(createPostWriter())
                .title("자바 프로젝트 시작합니다")
                .condition(ConditionTest.createCondition())
                .build();

        em.persist(post);
        return post;
    }

    private User createPostWriter() {
        User user = User.builder()
                .id(1L)
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        em.persist(user);
        return user;
    }
}