package com.olivejua.study.domain;

import com.olivejua.study.domain.board.*;
import com.olivejua.study.sampleData.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CommentTest {

    @Autowired
    TestEntityManager em;

    //TODO create 엔티티 메서드 리팩토링 해야함. 도메인에 아예 넣는건 어떨지 생각해보자.
    @Test
    @DisplayName("댓글[질문 게시판] 작성")
    public void writeComment_study() throws Exception {
        //given
        List<User> users = SampleUser.createList(2);
        users.forEach(user -> em.persist(user));
        User postWriter = users.get(0);
        User commentWriter = users.get(1);

        Question post = SampleQuestion.create(postWriter);
        em.persist(post);

        //when
        Comment comment =
                Comment.createComment(post, commentWriter, "참여하고 싶습니다");

        em.persist(comment);
        Comment findComment = em.find(Comment.class, comment.getId());

        //then
        assertEquals(comment.getId(), findComment.getId());
        assertEquals(comment.getPost().getId(), findComment.getPost().getId());
        assertEquals(comment.getWriter().getId(), findComment.getWriter().getId());
        assertEquals(comment.getContent(), findComment.getContent());
    }

    @Test
    @DisplayName("댓글[스터디 모집] 수정")
    void edit() {
        //given
        List<User> users = SampleUser.createList(2);
        users.forEach(user -> em.persist(user));
        User postWriter = users.get(0);
        User commentWriter = users.get(1);

        StudyRecruitment post = SampleStudyRecruitment.create(postWriter);
        em.persist(post);

        Comment comment = SampleComment.create(commentWriter, post);
        em.persist(comment);

        em.flush();
        em.clear();

        //when
        String changedContent = "저도 참여하고 싶습니다";
        comment.edit(changedContent);

        //then
        assertEquals(changedContent, comment.getContent());
    }
}