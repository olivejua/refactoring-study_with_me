package com.olivejua.study.repository;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.sampleData.SampleComment;
import com.olivejua.study.sampleData.SampleQuestion;
import com.olivejua.study.sampleData.SampleUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;
    
    @Test
    @DisplayName("comment - 저장")
    public void save() {
        List<User> users = SampleUser.createList(2); // user 2개 생성
        User commentWriter = users.get(0); // 댓글 작성자
        User postWriter = users.get(1); // 게시물 작성자

        users.forEach(user -> userRepository.save(user)); 

        Question post = SampleQuestion.create(postWriter); // 게시물 작성
        questionRepository.save(post);
        
        Comment comment = SampleComment.create(commentWriter, post); //댓글 작성

        //when
        commentRepository.save(comment);

        //then
        Comment findComment = commentRepository.findAll().get(0);

        Assertions.assertEquals(comment, findComment);
    }
}