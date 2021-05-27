package com.olivejua.study.repository.board;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.QComment;
import com.olivejua.study.domain.QUser;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.domain.board.QLink;
import com.olivejua.study.domain.board.QPlaceRecommendation;
import com.olivejua.study.sampleData.SamplePlaceRecommendation;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.place.PostListResponseDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.olivejua.study.domain.QComment.*;
import static com.olivejua.study.domain.QUser.*;
import static com.olivejua.study.domain.board.QLink.*;
import static com.olivejua.study.domain.board.QPlaceRecommendation.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaceRecommendationQueryRepositoryTest {

    @Autowired
    private PlaceRecommendationQueryRepository repository;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager em;

    private PlaceRecommendation post1;
    private PlaceRecommendation post2;
    private User writer;

    @BeforeEach
    void setup() {
        writer = SampleUser.create();
        em.persist(writer);

        List<String> links = new ArrayList<>();
        links.add("www.google.com");
        links.add("www.naver.com");
        links.add("www.daum.net");

        post1 = PlaceRecommendation.savePost(writer,
                "title1",
                "address1",
                "addressDetail1",
                "thumbnailPath1",
                "content1",
                links);

        post2 = PlaceRecommendation.savePost(writer,
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

    @Test
    void findComments() {
        em.persist(Comment.createComment(post1, writer, "example content1"));
        em.persist(Comment.createComment(post1, writer, "example content2"));
        em.persist(Comment.createComment(post1, writer, "example content3"));
        em.persist(Comment.createComment(post2, writer, "example content4"));
        em.persist(Comment.createComment(post2, writer, "example content5"));

        System.out.println("post1=" + post1.getId());
        System.out.println("post2=" + post2.getId());

        em.flush();
        em.clear();

        List<Tuple> tuple = queryFactory
                .select(
                        comment.post.id,
                        comment.count())
                .from(comment)
                .where(comment.post.id.in(2, 6))
                .groupBy(comment.post.id)
                .fetch();

        for (Tuple e : tuple) {
            System.out.println("postid=" + e.get(comment.post.id));
            System.out.println("comment count=" + e.get(comment.count()));
        }
    }

    @Test
    void testList() {
        em.persist(Comment.createComment(post1, writer, "example content1"));
        em.persist(Comment.createComment(post1, writer, "example content2"));
        em.persist(Comment.createComment(post1, writer, "example content3"));
        em.persist(Comment.createComment(post2, writer, "example content4"));
        em.persist(Comment.createComment(post2, writer, "example content5"));

        em.flush();
        em.clear();

        PageRequest paging = PageRequest.of(0, 20, Sort.Direction.ASC, "POST_ID");
        Page<PostListResponseDto> list = repository.list(paging);

        for (PostListResponseDto postListResponseDto : list) {
            System.out.println(postListResponseDto);
        }
    }
}


