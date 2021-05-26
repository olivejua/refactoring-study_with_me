package com.olivejua.study.service;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.Link;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.repository.CommentRepository;
import com.olivejua.study.repository.ReplyRepository;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.LikeHistoryRepository;
import com.olivejua.study.repository.board.LinkRepository;
import com.olivejua.study.repository.board.PlaceRecommendationQueryRepository;
import com.olivejua.study.repository.board.PlaceRecommendationRepository;
import com.olivejua.study.sampleData.SampleComment;
import com.olivejua.study.sampleData.SamplePlaceRecommendation;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.olivejua.study.domain.board.QLink.link;
import static com.olivejua.study.domain.board.QPlaceRecommendation.placeRecommendation;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PlaceServiceTest {

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceRecommendationRepository placeRecommendationRepository;

    @Autowired
    PlaceRecommendationQueryRepository placeRecommendationQueryRepository;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LikeHistoryRepository likeHistoryRepository;

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void setup() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    void read() {
        User writer = SampleUser.create();
        userRepository.save(writer);

        PlaceRecommendation post = SamplePlaceRecommendation.create(writer,
                new String[] {"www.google.com", "www.naver.com", "www.tistory.com", "www.daum.net", "www.github.com"});

        //when
        placeRecommendationRepository.save(post);
        post.getLinks().forEach(linkRepository::save);

        List<User> users = SampleUser.createList(5);
        users.forEach(userRepository::save);

        for (int i=0; i<5; i++) {
            likeHistoryRepository.save(
                    LikeHistory.createLikeHistory(post, users.get(i), true));
        }

        List<Comment> comments = SampleComment.createList(writer, post, 5);
        comments.forEach(commentRepository::save);

        em.flush();
        em.clear();

        PlaceRecommendation read = placeService.read(post.getId());

        List<String> links = read.getLinks().stream()
                .map(Link::getElement)
                .collect(Collectors.toList());

        assertEquals(links, Arrays.asList("www.google.com", "www.naver.com", "www.tistory.com", "www.daum.net", "www.github.com"));
    }

    @Test
    void test() {
        User writer = SampleUser.create();
        userRepository.save(writer);

        PlaceRecommendation post = SamplePlaceRecommendation.create(writer,
                new String[] {"www.google.com", "www.naver.com", "www.tistory.com", "www.daum.net", "www.github.com"});

        //when
        placeRecommendationRepository.save(post);
        post.getLinks().forEach(linkRepository::save);

        List<User> users = SampleUser.createList(5);
        users.forEach(userRepository::save);

        for (int i=0; i<5; i++) {
            likeHistoryRepository.save(
                    LikeHistory.createLikeHistory(post, users.get(i), true));
        }

        List<Comment> comments = SampleComment.createList(writer, post, 5);
        comments.forEach(commentRepository::save);

        em.flush();
        em.clear();

        PostReadResponseDto readDto = placeRecommendationQueryRepository.findReadDto(post.getId());
        assertEquals(5, readDto.getComments().size());
    }
}
