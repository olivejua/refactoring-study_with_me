package com.olivejua.study.service;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.CommentRepository;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.comment.CommentSaveRequestDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    private EntityManager em;

    private Question post1;
    private Question post2;
    private User writer;
    private Comment comment1;
    private Comment comment2;

    @BeforeEach
    void setup() {
        writer = SampleUser.create();
        em.persist(writer);

        post1 = Question.savePost(
                writer,
                "title1",
                "content1");

        post2 = Question.savePost(
                writer,
                "title2",
                "content2");

        em.persist(post1);
        em.persist(post2);

        comment1 = Comment.createComment(post1, writer, "comment1 content1");
        em.persist(comment1);
        em.persist(Comment.createComment(post1, writer, "comment1 content2"));
        em.persist(Comment.createComment(post2, writer, "comment2 content1"));

        comment2 = Comment.createComment(post2, writer, "comment2 content2");
        em.persist(comment2);
        em.persist(Comment.createComment(post2, writer, "comment2 content3"));
    }

    @Test
    void saveTest() {
        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(post1.getId(), "comment1 content3");
        Long savedCommentId = commentService.save(requestDto, writer);

        Comment savedComment = commentRepository.findById(savedCommentId).orElse(null);

        assertNotNull(savedComment);
        assertEquals(requestDto.getContent(), savedComment.getContent());
    }

    @Test
    void updateTest() {
        em.flush();
        em.clear();

        String updatedContent = "update comment1 content1";
        commentService.update(comment1.getId(), updatedContent);
        Comment comment = commentRepository.findById(comment1.getId()).orElse(null);

        assertNotNull(comment);
        assertEquals(comment1.getId(), comment.getId());
        assertEquals(updatedContent, comment.getContent());
    }

    @Test
    void deleteTest() {
        em.flush();
        em.clear();

        commentService.delete(comment2.getId());
        Comment comment = commentRepository.findById(comment2.getId()).orElse(null);

        assertNull(comment);
    }
}