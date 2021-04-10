package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CommentTest {

    @Autowired
    TestEntityManager em;

    //TODO create 엔티티 메서드 리팩토링 해야함. 도메인에 아예 넣는건 어떨지 생각해보자.
    @Test
    @DisplayName("댓글[스터디 모집] 작성")
    public void writeComment_study() throws Exception {
        //given
        StudyRecruitment post = createPost();
        User commentWriter = createCommentWriter();

        //when
        Comment comment = Comment.builder()
                .board(post)
                .writer(commentWriter)
                .content("참여하고 싶습니다")
                .build();

        em.persist(comment);
        Comment findComment = em.find(Comment.class, comment.getId());

        //then
        assertEquals(comment.getId(), findComment.getId());
        assertEquals(comment.getBoard().getId(), findComment.getBoard().getId());
        assertEquals(comment.getWriter().getId(), findComment.getWriter().getId());
        assertEquals(comment.getContent(), findComment.getContent());
    }

    @Test
    @DisplayName("댓글[스터디 모집] 수정")
    void edit() {
        //given
        StudyRecruitment post = createPost();
        Comment comment = createComment(post);

        //when
        String changedContent = "저도 참여하고 싶습니다";
        comment.edit(changedContent);

        //then
        assertEquals(changedContent, comment.getContent());
    }

    private Comment createComment(Board post) {
        Comment comment = Comment.builder()
                .board(post)
                .writer(createCommentWriter())
                .content("참여하고 싶습니다")
                .build();

        em.persist(comment);
        return comment;
    }

    private User createCommentWriter() {
        User writer = User.builder()
                .name("dreaming octopus")
                .email("dreamingoctopus@gmail.com")
                .role(Role.USER)
                .socialCode("google")
                .build();

        em.persist(writer);
        return writer;
    }

    private StudyRecruitment createPost() {
        StudyRecruitment post = StudyRecruitment.builder()
                .writer(createPostWriter())
                .title("자바 프로젝트 시작합니다")
                .condition(ConditionTest.createCondition())
                .build();

        em.persist(post);
        return post;
    }

    private User createPostWriter() {
        User writer = User.builder()
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        em.persist(writer);
        return writer;
    }
}