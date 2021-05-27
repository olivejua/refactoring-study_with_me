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
import com.olivejua.study.web.dto.board.place.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.place.like.LikeStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

        PostReadResponseDto responseDto = placeService.read(post.getId(), writer);

        assertEquals(5, responseDto.getComments().size());
        assertEquals(5, responseDto.getLikeCount());
        assertEquals(0, responseDto.getDislikeCount());
        assertEquals(LikeStatus.NOT_YET, responseDto.getLikeStatus());
    }

    @Test
    void testUpdate() {
        //given
        User writer = SampleUser.create();
        userRepository.save(writer);

        PlaceRecommendation post = SamplePlaceRecommendation.create(writer,
                new String[] {"www.google.com", "www.naver.com", "www.tistory.com", "www.daum.net", "www.github.com"});

        placeRecommendationRepository.save(post);
        post.getLinks().forEach(linkRepository::save);

        PostReadResponseDto readResponseDto = placeService.read(post.getId(), writer);

        //when
        String updatedTitle = post.getTitle() + " 수정";
        String updatedAddress = post.getAddress() + " 수정";

        PostSaveRequestDto requestDto = new PostSaveRequestDto(
                updatedTitle,
                updatedAddress,
                readResponseDto.getAddressDetail(),
                readResponseDto.getLinks(),
                readResponseDto.getThumbnailPath(),
                readResponseDto.getContent(),
                readResponseDto.getLikeCount(),
                readResponseDto.getDislikeCount(),
                readResponseDto.getViewCount());

        placeService.update(post.getId(), requestDto);

        em.flush();
        em.clear();

        PlaceRecommendation entity = placeRecommendationRepository.findById(post.getId()).orElse(null);
        assertNotNull(entity);
        assertEquals(updatedTitle, entity.getTitle());
        assertEquals(updatedAddress, entity.getAddress());
    }
}
