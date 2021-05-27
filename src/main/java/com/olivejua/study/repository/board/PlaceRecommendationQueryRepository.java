package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.web.dto.board.place.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.olivejua.study.domain.QComment.comment;
import static com.olivejua.study.domain.QUser.user;
import static com.olivejua.study.domain.board.QLikeHistory.likeHistory;
import static com.olivejua.study.domain.board.QLink.link;
import static com.olivejua.study.domain.board.QPlaceRecommendation.placeRecommendation;

@RequiredArgsConstructor
@Repository
public class PlaceRecommendationQueryRepository {

    private final JPAQueryFactory queryFactory;


    public Optional<PlaceRecommendation> findEntity(Long postId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(placeRecommendation)
                .join(placeRecommendation.writer, user)
                .leftJoin(placeRecommendation.links, link)
                .leftJoin(placeRecommendation.likes, likeHistory)
                .leftJoin(placeRecommendation.comment, comment)
                .where(idEq(postId))
                .fetchOne());
    }

    public Optional<LikeHistory> getLikeStatusByPostAndUser(Long postId, Long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(likeHistory)
                .join(likeHistory.user, user)
                .join(likeHistory.post, placeRecommendation)
                .where(
                        user.id.eq(userId),
                        placeRecommendation.id.eq(postId))
                .fetchOne());
    }

//    public Page<PostListResponseDto> list(Pageable pageable) {
//
//    }
//
//    private List<PostListResponseDto> findPosts(Pageable pageable, SearchDto cond) {
//        queryFactory
//                .selectFrom(placeRecommendation)
//                .innerJoin(placeRecommendation.writer, user)
//                .leftJoin(placeRecommendation.links, link)
//                .leftJoin(placeRecommendation.comment, comment)
//                .offset(pageable.getOffset())
//                .limit()
//    }

    private BooleanExpression idEq(Long postId) {
        return placeRecommendation.id.eq(postId);
    }
}
