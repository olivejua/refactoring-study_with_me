package com.olivejua.study.service;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.repository.board.LikeHistoryRepository;
import com.olivejua.study.repository.board.LinkRepository;
import com.olivejua.study.repository.board.PlaceRecommendationRepository;
import com.olivejua.study.sampleData.SamplePlaceRecommendation;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.olivejua.study.web.dto.board.place.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.place.like.LikeStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class PlaceServiceTest extends CommonBoardServiceTest {

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceRecommendationRepository placeRecommendationRepository;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    LikeHistoryRepository likeHistoryRepository;

    @AfterEach
    void cleanup() {
        replyRepository.deleteAll();
        commentRepository.deleteAll();
        likeHistoryRepository.deleteAll();
        linkRepository.deleteAll();
        placeRecommendationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void post() {
        List<String> links =
                Arrays.asList("test post sample link1",
                        "test post sample link2",
                        "test post sample link3");

        PostSaveRequestDto requestDto = new PostSaveRequestDto(
                "test post sample title",
                "test post sample address",
                "test post sample addressDetail",
                links,
                "test post sample thumbnailPath",
                "test post sample content");

        Long postId = placeService.post(requestDto, dummyPostWriter);

        PlaceRecommendation findEntity =
                placeRecommendationRepository.findById(postId).orElse(null);

        assertNotNull(findEntity);
        assertEquals(requestDto.getTitle(), findEntity.getTitle());
        assertEquals(requestDto.getAddress(), findEntity.getAddress());
        assertEquals(requestDto.getAddressDetail(), findEntity.getAddressDetail());
        assertEquals(requestDto.getContent(), findEntity.getContent());
    }

    @Test
    void read() {
        PostReadResponseDto responseDto = placeService.read(dummyPost.getId(), dummyPostWriter, dummyServletPath);

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

        PostReadResponseDto readResponseDto = placeService.read(post.getId(), writer, dummyServletPath);

        //when
        String updatedTitle = post.getTitle() + " 수정";
        String updatedAddress = post.getAddress() + " 수정";

        PostSaveRequestDto requestDto = new PostSaveRequestDto(
                updatedTitle,
                updatedAddress,
                readResponseDto.getAddressDetail(),
                readResponseDto.getLinks(),
                readResponseDto.getThumbnailPath(),
                readResponseDto.getContent());

        placeService.update(post.getId(), requestDto);

        em.flush();
        em.clear();

        PlaceRecommendation entity = placeRecommendationRepository.findById(post.getId()).orElse(null);
        assertNotNull(entity);
        assertEquals(updatedTitle, entity.getTitle());
        assertEquals(updatedAddress, entity.getAddress());
    }

    List<String> toList(String[] array) {

        List<String> list = new ArrayList<>(Arrays.asList(array));

        return list;
    }

    @Override
    void saveDummyPost(Board post) {
        if (post instanceof PlaceRecommendation) {
            PlaceRecommendation postInPlace = (PlaceRecommendation) post;
            placeRecommendationRepository.save(postInPlace);
            postInPlace.getLinks().forEach(linkRepository::save);

            dummyPost = postInPlace;
            saveDummyLikes();
        }
    }

    @Override
    Board createDummyPost() {
        List<String> links = new ArrayList<>();
        links.add("sample link1");
        links.add("sample link2");
        links.add("sample link3");

        return PlaceRecommendation.savePost(
                dummyPostWriter,
                "sample title",
                "sample address",
                "sample addressDetail",
                "smaple thumbnailPath",
                "sample content1",
                links
        );
    }

    void saveDummyLikes() {
        User user1 = User.createUser("likeUser1", "likeUser1@google.com", Role.USER, "google");
        User user2 = User.createUser("likeUser2", "likeUser2@google.com", Role.USER, "google");
        User user3 = User.createUser("likeUser3", "likeUser3@google.com", Role.USER, "google");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        LikeHistory likeHistory1 = LikeHistory.createLikeHistory((PlaceRecommendation) dummyPost, user1, true);
        LikeHistory likeHistory2 = LikeHistory.createLikeHistory((PlaceRecommendation) dummyPost, user2, true);
        LikeHistory likeHistory3 = LikeHistory.createLikeHistory((PlaceRecommendation) dummyPost, user3, false);
        likeHistoryRepository.save(likeHistory1);
        likeHistoryRepository.save(likeHistory2);
        likeHistoryRepository.save(likeHistory3);
    }
}
