package com.olivejua.study.repository.board;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.sampleData.SamplePlaceRecommendation;
import com.olivejua.study.sampleData.SampleUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PlaceRecommendationRepositoryTest {

    @Autowired
    PlaceRecommendationRepository placeRecommendationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LinkRepository linkRepository;

    @Test
    @DisplayName("PlaceRecommendation - 저장")
    public void save() {
        User writer = SampleUser.create();
        userRepository.save(writer);

        PlaceRecommendation post = SamplePlaceRecommendation.create(writer,
                new String[] {"www.google.com", "www.naver.com", "www.tistory.com", "www.daum.net", "www.github.com"});

        //when
        placeRecommendationRepository.save(post);
        post.getLinks().forEach(linkRepository::save);

        //then
        PlaceRecommendation findPost = placeRecommendationRepository.findAll().get(0);

        assertEquals(post, findPost);
        assertEquals(post.getId(), findPost.getId());
        assertEquals(post.getWriter(), findPost.getWriter());
        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getAddress(), findPost.getAddress());
        assertEquals(post.getAddressDetail(), findPost.getAddressDetail());
        assertEquals(post.getLinks(), findPost.getLinks());
        assertEquals(post.getContent(), findPost.getContent());
        assertEquals(post.getThumbnailName(), findPost.getThumbnailName());
    }
}
