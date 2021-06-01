package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PlaceRecommendationTest {

    @Autowired
    EntityManager em;

    private User writer;
    private List<String> links;

    @BeforeEach
    void setup() {
        writer = User.createUser("user1", "user1@gmail.com", Role.USER, "google");
        em.persist(writer);

        links = new ArrayList<>();
        links.add("sample link1");
        links.add("sample link2");
        links.add("sample link3");
        links.add("sample link4");
    }


    @Test
    @DisplayName("장소 추천 - 게시글 작성")
    public void write() {
        //when
        PlaceRecommendation post = PlaceRecommendation.savePost(
                writer,
                "title1",
                "address1",
                "addressDetail",
                "thumbnailPath1",
                "content1",
                links);
        em.persist(post);
        post.getLinks().forEach(em::persist);

        em.flush();
        em.clear();

        PlaceRecommendation findPost = em.find(PlaceRecommendation.class, post.getId());

        //then
        assertEquals(post.getId(), findPost.getId());
        assertEquals(post.getWriter().getId(), findPost.getWriter().getId());
        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getAddress(), post.getAddress());
        assertEquals(post.getAddressDetail(), post.getAddressDetail());
        assertEquals(post.getThumbnailPath(), post.getThumbnailPath());
        assertEquals(post.getContent(), post.getContent());
    }

    @Test
    @DisplayName("장소 추천 - 게시글 수정")
    void edit() {
        //given
        PlaceRecommendation post = PlaceRecommendation.savePost(
                writer,
                "title1",
                "address1",
                "addressDetail",
                "thumbnailPath1",
                "content1",
                links);
        em.persist(post);
        post.getLinks().forEach(em::persist);

        em.flush();
        em.clear();

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
}