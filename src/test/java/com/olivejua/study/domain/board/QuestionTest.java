package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuestionTest {

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("질문 게시판 - 게시글 작성")
    public void write() {
        //given
        Question post = createPost();

        //when
        Question findPost = em.find(Question.class, post.getId());

        //then
        assertEquals(post, findPost);
    }

    @Test
    @DisplayName("스터디 모집 - 게시글 수정")
    void edit() {
        //given
        Question post = createPost();

        //when
        post.edit("jpa 관련 질문입니다-수정", "jpa에서 커밋은 어느시점에 하는건가요?");

        //then
        Question findPost = em.find(Question.class, post.getId());

        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getContent(), findPost.getContent());
    }

    private Question createPost() {
        Question newPost = Question.savePost(
                createWriter(),
                "jpa 관련질문입니다.",
                "영속성 컨텍스트는 동일한 객체를 보장해주나요?");
        em.persist(newPost);
        return newPost;
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