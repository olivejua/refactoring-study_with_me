package com.olivejua.study.repository.board;

import com.olivejua.study.domain.*;
import com.olivejua.study.domain.board.*;
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
import static com.olivejua.study.domain.board.QLikeHistory.*;
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


        User user2 = User.createUser("user2", "user2@gmail.com", Role.USER, "google");
        User user3 = User.createUser("user3", "user3@gmail.com", Role.USER, "google");
        User user4 = User.createUser("user4", "user4@gmail.com", Role.USER, "google");
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);

        // post1: like-2, dislike-1
        em.persist(LikeHistory.createLikeHistory(post1, user2, true));
        em.persist(LikeHistory.createLikeHistory(post1, user3, true));
        em.persist(LikeHistory.createLikeHistory(post1, user4, false));

        // post2: like-1, dislike-2
        em.persist(LikeHistory.createLikeHistory(post2, user2, true));
        em.persist(LikeHistory.createLikeHistory(post2, user3, false));
        em.persist(LikeHistory.createLikeHistory(post2, user4, false));


        em.flush();
        em.clear();

        PageRequest paging = PageRequest.of(0, 20, Sort.Direction.ASC, "POST_ID");
        Page<PostListResponseDto> list = repository.list(paging);

        for (PostListResponseDto postListResponseDto : list) {
            System.out.println(postListResponseDto.getPostId());
            System.out.println(postListResponseDto.getLikeCount());
            System.out.println(postListResponseDto.getDislikeCount());
        }
    }

    @Test
    void testLikeCount() {
        em.persist(Comment.createComment(post1, writer, "example content1"));
        em.persist(Comment.createComment(post1, writer, "example content2"));
        em.persist(Comment.createComment(post1, writer, "example content3"));
        em.persist(Comment.createComment(post2, writer, "example content4"));
        em.persist(Comment.createComment(post2, writer, "example content5"));


        User user2 = User.createUser("user2", "user2@gmail.com", Role.USER, "google");
        User user3 = User.createUser("user3", "user3@gmail.com", Role.USER, "google");
        User user4 = User.createUser("user4", "user4@gmail.com", Role.USER, "google");
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);

        // post1: like-2, dislike-1
        em.persist(LikeHistory.createLikeHistory(post1, user2, true));
        em.persist(LikeHistory.createLikeHistory(post1, user3, true));
        em.persist(LikeHistory.createLikeHistory(post1, user4, false));

        // post2: like-1, dislike-2
        em.persist(LikeHistory.createLikeHistory(post2, user2, true));
        em.persist(LikeHistory.createLikeHistory(post2, user3, false));
        em.persist(LikeHistory.createLikeHistory(post2, user4, false));

        em.flush();
        em.clear();

        List<LikeHistory> histories = queryFactory
                .selectFrom(likeHistory)
                .where(likeHistory.post.id.in(post1.getId()))
                .fetch();

        for (LikeHistory history : histories) {
            System.out.println("id=" + history.getPost().getId());
            System.out.println("isLike=" + history.isLike());
        }

        long count = queryFactory
                .selectFrom(likeHistory)
                .where(
                        likeHistory.post.id.in(post1.getId()),
                        likeHistory.isLike.isTrue())
                .fetchCount();

        System.out.println("count=" + count);
    }
}


