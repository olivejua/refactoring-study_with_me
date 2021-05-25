package com.olivejua.study.repository.board;

import com.olivejua.study.domain.QUser;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.domain.board.QLink;
import com.olivejua.study.domain.board.QPlaceRecommendation;
import com.olivejua.study.sampleData.SamplePlaceRecommendation;
import com.olivejua.study.sampleData.SampleUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.olivejua.study.domain.QUser.*;
import static com.olivejua.study.domain.board.QLink.*;
import static com.olivejua.study.domain.board.QPlaceRecommendation.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaceRecommendationQueryRepositoryTest {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setup() {
        User writer = SampleUser.create();
        em.persist(writer);

        List<String> links = new ArrayList<>();
        links.add("www.google.com");
        links.add("www.naver.com");
        links.add("www.daum.net");

        PlaceRecommendation post1 = PlaceRecommendation.savePost(writer,
                "title1",
                "address1",
                "addressDetail1",
                "thumbnailPath1",
                "content1",
                links);

        PlaceRecommendation post2 = PlaceRecommendation.savePost(writer,
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
    void findPostTest() {
        em.flush();
        em.clear();

        List<PlaceRecommendation> list = queryFactory
                .selectFrom(QPlaceRecommendation.placeRecommendation)
                .fetch();

        assertEquals(2, list.size());
        assertThat(list).extracting("title")
                .containsExactly("title1", "title2");
    }
}


