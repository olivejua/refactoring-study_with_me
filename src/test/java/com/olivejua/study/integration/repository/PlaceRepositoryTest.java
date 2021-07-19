package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.repository.board.LinkRepository;
import com.olivejua.study.repository.board.PlaceRecommendationQueryRepository;
import com.olivejua.study.repository.board.PlaceRecommendationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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


    /**
     * test template zone
     */
    @Override
    void setup() {}

    @Override
    void clearAll() {
        linkRepository.deleteAll();
        placeRepository.deleteAll();
    }


    /**
     * test zone
     */
    @Test
    void testFindEntity() {

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
        return placeRepository.save(post);
    }
}
