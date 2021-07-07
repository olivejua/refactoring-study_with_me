package com.olivejua.study.repository.board;

import com.olivejua.study.domain.*;
import com.olivejua.study.domain.board.*;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.place.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
import static com.olivejua.study.domain.board.QLikeHistory.*;
import static com.olivejua.study.domain.board.QPlaceRecommendation.*;
import static com.querydsl.jpa.JPAExpressions.*;
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
        em.persist(Comment.createComment(post1, writer, "post1 content1"));
        em.persist(Comment.createComment(post1, writer, "post1 content2"));
        em.persist(Comment.createComment(post1, writer, "post1 content3"));
        em.persist(Comment.createComment(post2, writer, "post2 content1"));
        em.persist(Comment.createComment(post2, writer, "post2 content2"));

        User likeUser1 = User.createUser("like user1", "likeUser1@gmail.com", Role.USER, "google");
        User likeUser2 = User.createUser("like user2", "likeUser2@gmail.com", Role.USER, "google");
        User likeUser3 = User.createUser("like user3", "likeUser3@gmail.com", Role.USER, "google");
        User likeUser4 = User.createUser("like user4", "likeUser4@gmail.com", Role.USER, "google");
        User likeUser5 = User.createUser("like user5", "likeUser5@gmail.com", Role.USER, "google");
        em.persist(likeUser1);
        em.persist(likeUser2);
        em.persist(likeUser3);
        em.persist(likeUser4);
        em.persist(likeUser5);

        //post1: like-2, dislike-1
        em.persist(LikeHistory.createLikeHistory(post1, likeUser1, true));
        em.persist(LikeHistory.createLikeHistory(post1, likeUser2, true));
        em.persist(LikeHistory.createLikeHistory(post1, likeUser3, false));

        //post2: like-1, dislike-1
        em.persist(LikeHistory.createLikeHistory(post2, likeUser4, true));
        em.persist(LikeHistory.createLikeHistory(post2, likeUser5, false));

        em.flush();
        em.clear();

        PageRequest paging = PageRequest.of(0, 20, Sort.Direction.ASC, "POST_ID");
        Page<PostListResponseDto> results = repository.findEntities(paging);
        List<PostListResponseDto> list = results.getContent();

        assertEquals(2, list.size());

        PostListResponseDto post1 = list.get(0);
        assertEquals(3, post1.getCommentCount());
        assertEquals(2, post1.getLikeCount());
        assertEquals(1, post1.getDislikeCount());

        PostListResponseDto post2 = list.get(1);
        assertEquals(2, post2.getCommentCount());
        assertEquals(1, post2.getLikeCount());
        assertEquals(1, post2.getDislikeCount());
    }

    @Test
    void testSearch() {
        em.persist(Comment.createComment(post1, writer, "post1 content1"));
        em.persist(Comment.createComment(post1, writer, "post1 content2"));
        em.persist(Comment.createComment(post1, writer, "post1 content3"));
        em.persist(Comment.createComment(post2, writer, "post2 content1"));
        em.persist(Comment.createComment(post2, writer, "post2 content2"));

        User likeUser1 = User.createUser("like user1", "likeUser1@gmail.com", Role.USER, "google");
        User likeUser2 = User.createUser("like user2", "likeUser2@gmail.com", Role.USER, "google");
        User likeUser3 = User.createUser("like user3", "likeUser3@gmail.com", Role.USER, "google");
        User likeUser4 = User.createUser("like user4", "likeUser4@gmail.com", Role.USER, "google");
        User likeUser5 = User.createUser("like user5", "likeUser5@gmail.com", Role.USER, "google");
        em.persist(likeUser1);
        em.persist(likeUser2);
        em.persist(likeUser3);
        em.persist(likeUser4);
        em.persist(likeUser5);

        //post1: like-2, dislike-1
        em.persist(LikeHistory.createLikeHistory(post1, likeUser1, true));
        em.persist(LikeHistory.createLikeHistory(post1, likeUser2, true));
        em.persist(LikeHistory.createLikeHistory(post1, likeUser3, false));

        //post2: like-1, dislike-1
        em.persist(LikeHistory.createLikeHistory(post2, likeUser4, true));
        em.persist(LikeHistory.createLikeHistory(post2, likeUser5, false));

        em.flush();
        em.clear();

        PageRequest paging = PageRequest.of(0, 20, Sort.Direction.ASC, "POST_ID");
        SearchDto searchDto = new SearchDto(SearchType.TITLE.name(), "tle1");
        Page<PostListResponseDto> results1 = repository.findEntitiesWith(searchDto, paging);
        List<PostListResponseDto> list1 = results1.getContent();

        assertEquals(1, list1.size());
        assertEquals("title1", list1.get(0).getTitle());

        searchDto = new SearchDto(SearchType.ADDRESS.name(), "ress2");
        Page<PostListResponseDto> results2 = repository.findEntitiesWith(searchDto, paging);
        List<PostListResponseDto> list2 = results2.getContent();

        assertEquals(1, list2.size());
        assertEquals("title2", list2.get(0).getTitle());


        searchDto = new SearchDto(SearchType.CONTENT.name(), "content");
        Page<PostListResponseDto> results3 = repository.findEntitiesWith(searchDto, paging);
        List<PostListResponseDto> list3 = results3.getContent();

        assertEquals(2, list3.size());
    }

    @Test
    void testMultiColumnsCountsQuery() {
        //post1- 3 , post2- 2
        em.persist(Comment.createComment(post1, writer, "post1 content1"));
        em.persist(Comment.createComment(post1, writer, "post1 content2"));
        em.persist(Comment.createComment(post1, writer, "post1 content3"));
        em.persist(Comment.createComment(post2, writer, "post2 content1"));
        em.persist(Comment.createComment(post2, writer, "post2 content2"));

        User likeUser1 = User.createUser("like user1", "likeUser1@gmail.com", Role.USER, "google");
        User likeUser2 = User.createUser("like user2", "likeUser2@gmail.com", Role.USER, "google");
        User likeUser3 = User.createUser("like user3", "likeUser3@gmail.com", Role.USER, "google");
        User likeUser4 = User.createUser("like user4", "likeUser4@gmail.com", Role.USER, "google");
        User likeUser5 = User.createUser("like user5", "likeUser5@gmail.com", Role.USER, "google");
        em.persist(likeUser1);
        em.persist(likeUser2);
        em.persist(likeUser3);
        em.persist(likeUser4);
        em.persist(likeUser5);

        //post1: like-2, dislike-1
        em.persist(LikeHistory.createLikeHistory(post1, likeUser1, true));
        em.persist(LikeHistory.createLikeHistory(post1, likeUser2, true));
        em.persist(LikeHistory.createLikeHistory(post1, likeUser3, false));

        //post2: like-1, dislike-1
        em.persist(LikeHistory.createLikeHistory(post2, likeUser4, true));
        em.persist(LikeHistory.createLikeHistory(post2, likeUser5, false));

        em.flush();
        em.clear();

        List<Long> postIds = new ArrayList<>();
//        postIds.add(2L);
        postIds.add(6L);

        List<Tuple> result = queryFactory
                .select(
                        placeRecommendation.id,
                        ExpressionUtils.as(select(comment.count())
                            .from(comment)
                            .where(comment.post.id.eq(placeRecommendation.id)),"commentCount"),
                        ExpressionUtils.as(select(likeHistory.count())
                            .from(likeHistory)
                            .where(
                                    likeHistory.post.id.eq(placeRecommendation.id),
                                    likeHistory.isLike.isTrue()), "likeCount"),
                        ExpressionUtils.as(select(likeHistory.count())
                                .from(likeHistory)
                                .where(
                                        likeHistory.post.id.eq(placeRecommendation.id),
                                        likeHistory.isLike.isFalse()), "dislikeCount")
                ).from(placeRecommendation)
                .where(placeRecommendation.id.in(postIds))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
            System.out.println("postId = " + tuple.get(placeRecommendation.id));
            NumberPath<Long> commentCount = Expressions.numberPath(Long.class, "commentCount");
            NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");
            NumberPath<Long> dislikeCount = Expressions.numberPath(Long.class, "dislikeCount");
            System.out.println("commentCount = " + tuple.get(commentCount));
            System.out.println("likeCount = " + tuple.get(likeCount));
            System.out.println("dislikeCount = " + tuple.get(dislikeCount));
        }
    }
}


