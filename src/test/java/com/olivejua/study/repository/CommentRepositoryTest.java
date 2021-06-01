package com.olivejua.study.repository;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.domain.board.QBoard;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.sampleData.SampleComment;
import com.olivejua.study.sampleData.SampleQuestion;
import com.olivejua.study.sampleData.SampleUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.olivejua.study.domain.board.QBoard.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager em;

    private PlaceRecommendation post1;
    private PlaceRecommendation post2;
    private User writer;

    @BeforeEach
    void setup() {
        writer = SampleUser.create();
        em.persist(writer);

        List<String> links = new ArrayList<>();
        links.add("www.google.com");
        links.add("www.naver.com");
        links.add("www.daum.net");

        post1 = PlaceRecommendation.savePost(writer,
                "title1",
                "address1",
                "addressDetail1",
                "thumbnailPath1",
                "content1",
                links);

        post2 = PlaceRecommendation.savePost(writer,
                "title2",
                "address2",
                "addressDetail2",
                "thumbnailPath2",
                "content2",
                links);

        em.persist(post1);
        post1.getLinks().forEach(em::persist);
        em.persist(post2);
        post2.getLinks().forEach(em::persist);
    }
    
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

        assertEquals(comment, findComment);
    }

    @Test
    void findPostInBoard() {
        em.flush();
        em.clear();

        Board post1 = queryFactory
                .selectFrom(QBoard.board)
                .where(QBoard.board.id.eq(this.post1.getId()))
                .fetchOne();

        assertNotNull(post1);
        assertEquals(post1.getId(), post1.getId());
    }
}