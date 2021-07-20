package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.repository.board.LikeHistoryRepository;
import com.olivejua.study.repository.board.LinkRepository;
import com.olivejua.study.repository.board.PlaceRecommendationQueryRepository;
import com.olivejua.study.repository.board.PlaceRecommendationRepository;
import com.olivejua.study.web.dto.board.place.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceRepositoryTest extends CommonBoardRepositoryTest {

    /**
     * Dependency Injection
     */
    @Autowired
    private PlaceRecommendationRepository placeRepository;

    @Autowired
    private PlaceRecommendationQueryRepository placeQueryRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LikeHistoryRepository likeHistoryRepository;

    /**
     * test template zone
     */
    @Override
    void setup() {}

    @Override
    void clearAll() {
        likeHistoryRepository.deleteAll();
        linkRepository.deleteAll();
        placeRepository.deleteAll();
    }


    /**
     * test zone
     */
    @Test
    @DisplayName("하나의 entity를 조회한다")
    void testFindEntity() {
        //given
        PlaceRecommendation post = (PlaceRecommendation) dummyPosts.get(1);
        em.flush();
        em.clear();

        //when
        PlaceRecommendation findPost = placeQueryRepository.findEntity(post.getId()).orElse(null);

        //given
        assertNotNull(findPost);
        assertEquals(post.getId(), findPost.getId());
        assertEquals(post.getWriter().getId(), findPost.getWriter().getId());
        assertTrue(findPost.getLinks().containsAll(post.getLinks()));
        assertEquals(post.getLikes().size(), findPost.getLikes().size());
        assertTrue(post.getLikes().containsAll(findPost.getLikes()));
        assertEquals(post.getComment().size(), findPost.getComment().size());
    }

    @Test
    @DisplayName("여러개의 entities를 조회한다")
    void testFindEntities() {
        //given
        PageRequest paging = PageRequest.of(0, 10);

        //when
        Page<PostListResponseDto> entities = placeQueryRepository.findEntities(paging);

        //then
        assertEquals(dummyPosts.size(), entities.getTotalElements());
        assertEquals(10, entities.getContent().size());

        PostListResponseDto findPost = entities.getContent().get(0);
        assertEquals(dummyWriter.getName(), findPost.getWriterName());

        PlaceRecommendation expectedPost = (PlaceRecommendation) dummyPosts.get(0);
        assertEquals(expectedPost.getId(), findPost.getPostId());
        assertEquals(expectedPost.getComment().size(), findPost.getCommentCount());

        long likeCount = expectedPost.getLikes()
                .stream().filter(LikeHistory::isLike).count();
        long dislikeCount = expectedPost.getLikes()
                .stream().filter(like -> !like.isLike()).count();

        assertEquals(likeCount, findPost.getLikeCount());
        assertEquals(dislikeCount,  findPost.getDislikeCount());
    }

    @Test
    @DisplayName("검색 조건에 맞는 여러개의 entities를 조회한다")
    void testFindEntitiesWith() {
        //given
        PlaceRecommendation expectedPost = PlaceRecommendation.savePost(
                dummyWriter,
                "search target title",
                "search target address",
                "search target address detail",
                "search target thumbnailPath",
                "search target content",
                List.of("www.github.com", "www.naver.com", "www.google.com"));

        dummyPosts.add(placeRepository.save(expectedPost));

        PageRequest paging = PageRequest.of(0, 10);
        SearchDto searchDto = new SearchDto(SearchType.ADDRESS.name(), "target");

        //when
        Page<PostListResponseDto> entities = placeQueryRepository.findEntitiesWith(searchDto, paging);

        //then
        assertEquals(1, entities.getTotalElements());

        PostListResponseDto findPost = entities.getContent().get(0);
        assertEquals(expectedPost.getWriter().getName(), findPost.getWriterName());
        assertEquals(expectedPost.getComment().size(), findPost.getCommentCount());

        long likeCount = expectedPost.getLikes()
                .stream().filter(LikeHistory::isLike).count();
        long dislikeCount = expectedPost.getLikes()
                .stream().filter(like -> !like.isLike()).count();

        assertEquals(likeCount, findPost.getLikeCount());
        assertEquals(dislikeCount, findPost.getDislikeCount());
    }

    @Test
    @DisplayName("특정회원의 해당게시물 좋아요 여부를 찾아온다")
    void testFindLikeHistoryByPostAndUser() {
        //given
        User user = createDummyUser();
        PlaceRecommendation post = (PlaceRecommendation) dummyPosts.get(0);
        createLikeHistory(post, user, true);

        em.flush();
        em.clear();

        //when
        LikeHistory findLike = placeQueryRepository.
                findLikeHistoryByPostAndUser(post.getId(), user.getId()).orElse(null);

        //then
        assertNotNull(findLike);
        assertTrue(findLike.isLike());
    }


    /**
     * overriding zone
     */
    @Override
    Board createDummyPost() {
        PlaceRecommendation post = PlaceRecommendation.savePost(
                dummyWriter,
                "sample title",
                "sample address",
                "sample addressDetail",
                "sample thumbnail",
                "sample content",
                List.of("www.naver.com", "www.google.com", "www.github.com"));

        post.getLinks().forEach(linkRepository::save);
        placeRepository.save(post);
        createDummyLikeHistories(post);

        return post;
    }

    void createDummyLikeHistories(PlaceRecommendation post) {
        for (int i=0; i<3; i++) {
            createLikeHistory(post, createDummyUser(), i % 2 == 0);
        }
    }

    void createLikeHistory(PlaceRecommendation post, User user, boolean isLike) {
        likeHistoryRepository.save(
                LikeHistory.createLikeHistory(post, user, isLike));
    }
}
