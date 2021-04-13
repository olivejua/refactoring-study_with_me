package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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

        //when
        post.edit("강남 스터디카페 추천합니다. -수정", 
                "서울시 강남구-수정",
                "강남역 1번출구 노란건물 1층 -수정",
                "/abc/def/gh-수정",
                "한번 가보시길.. -수정",
                LinkTest.createLinks(new String[] {"www.google.com"}));

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
        PlaceRecommendation post = PlaceRecommendation.builder()
                .writer(createWriter())
                .title("강남 스터디카페 추천합니다.")
                .address("서울시 강남구")
                .addressDetail("강남역 1번출구 노란건물 1층")
                .thumbnailPath("/abc/def/gh")
                .content("한번 가보시길.. 좋아요 눌러주세요.")
                .links(LinkTest.createLinks(new String[] {"www.google.com", "www.naver.com"}))
                .build();

        em.persist(post);
        return post;
    }

    private User createWriter() {
        User user = User.builder()
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        em.persist(user);
        return user;
    }
}