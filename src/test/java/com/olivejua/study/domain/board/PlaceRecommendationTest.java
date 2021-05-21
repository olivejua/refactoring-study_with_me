package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PlaceRecommendationTest {

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("장소 추천 - 게시글 작성")
    public void write() {
        PlaceRecommendation post = createPost();

        PlaceRecommendation findPost = em.find(PlaceRecommendation.class, post.getId());

        assertEquals(post, findPost);
    }

    @Test
    @DisplayName("장소 추천 - 게시글 수정")
    void edit() {
        //given
        PlaceRecommendation post = createPost();
        List<String> links = new ArrayList<>();
        links.add("www.google.com");

        //when
        post.edit("강남 스터디카페 추천합니다. -수정", 
                "서울시 강남구-수정",
                "강남역 1번출구 노란건물 1층 -수정",
                "/abc/def/gh-수정",
                "한번 가보시길.. -수정",
                Arrays.asList("www.google.com"));

        //then
        PlaceRecommendation findPost = em.find(PlaceRecommendation.class, post.getId());

        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getAddress(), findPost.getAddress());
        assertEquals(post.getAddressDetail(), findPost.getAddressDetail());
        assertEquals(post.getThumbnailPath(), findPost.getThumbnailPath());
        assertEquals(post.getContent(), findPost.getContent());
        assertEquals(post.getLinks(), findPost.getLinks());
    }

    private PlaceRecommendation createPost() {
        List<String> links = new ArrayList<>();
        links.add("www.google.com");
        links.add("www.naver.com");

        PlaceRecommendation post = PlaceRecommendation.savePost(
                createWriter(), "강남 스터디카페 추천합니다.",
                "서울시 강남구", "강남역 1번출구 노란건물 1층",
                "/abc/def/gh", "한번 가보시길.. 좋아요 눌러주세요.",
                links);

        em.persist(post);
        return post;
    }

    private User createWriter() {
        User user = User.createUser(
                "김슬기",
                "tmfrl4710@gmail.com",
                Role.GUEST,
                "google"
        );

        em.persist(user);
        return user;
    }
}