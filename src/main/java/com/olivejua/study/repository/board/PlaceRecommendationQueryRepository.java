package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.domain.board.QLikeHistory;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.olivejua.study.domain.QComment.comment;
import static com.olivejua.study.domain.QUser.user;
import static com.olivejua.study.domain.board.QLikeHistory.*;
import static com.olivejua.study.domain.board.QLink.link;
import static com.olivejua.study.domain.board.QPlaceRecommendation.placeRecommendation;

@RequiredArgsConstructor
@Repository
public class PlaceRecommendationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<PlaceRecommendation> findEntity(Long postId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(placeRecommendation)
                .innerJoin(placeRecommendation.writer, user)
                .leftJoin(placeRecommendation.links, link)
                .leftJoin(placeRecommendation.comment, comment)
                .where(idEq(postId))
                .fetchOne());
    }

    public PostReadResponseDto findReadDto(Long postId) {
        return queryFactory
                .select(Projections.constructor(
                        PostReadResponseDto.class,
                        placeRecommendation.id,
                        placeRecommendation.title,
                        placeRecommendation.writer,
                        placeRecommendation.address,
                        placeRecommendation.addressDetail,
                        placeRecommendation.thumbnailPath,
                        placeRecommendation.content,
                        placeRecommendation.links,
                        JPAExpressions
                                .select(likeHistory.count())
                                .from(likeHistory)
                                .where(likeHistory.post.id.eq(postId), likeHistory.isLike.isTrue()),
                        JPAExpressions
                                .select(likeHistory.count())
                                .from(likeHistory)
                                .where(likeHistory.post.id.eq(postId), likeHistory.isLike.isFalse()),
                        placeRecommendation.viewCount,
                        placeRecommendation.createdDate,
                        placeRecommendation.comment
                ))
                .from(placeRecommendation)
                .innerJoin(placeRecommendation.writer, user)
                .leftJoin(placeRecommendation.links, link)
                .leftJoin(placeRecommendation.comment, comment)
                .where(idEq(postId))
                .fetchOne();
    }

    private BooleanExpression idEq(Long postId) {
        return placeRecommendation.id.eq(postId);
    }
}
