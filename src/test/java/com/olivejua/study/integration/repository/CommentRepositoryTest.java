package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.olivejua.study.domain.QComment.comment;
import static org.junit.jupiter.api.Assertions.*;

public class CommentRepositoryTest extends CommonBoardRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    /**
     * test template zone
     */
    @Override
    void setup() {}

    @Override
    void clearAll() {
        
    }

    @Test
    void testDeleteCommentsByPost() {
        //given
        Board post = dummyPosts.get(0);

        //when
        commentRepository.deleteCommentsByPost(post);

        //then
        List<Comment> findComments = queryFactory.selectFrom(comment)
                .where(comment.post.id.eq(post.getId()))
                .fetch();

        assertEquals(0, findComments.size());
    }

    @Override
    Board createDummyPost() {
        Question post = Question.savePost(dummyWriter, "sample title", "sample content");
        return questionRepository.save(post);
    }
}
