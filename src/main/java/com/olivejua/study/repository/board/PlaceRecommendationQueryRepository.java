package com.olivejua.study.repository.board;

import com.olivejua.study.domain.QComment;
import com.olivejua.study.domain.QUser;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.domain.board.QLikeHistory;
import com.olivejua.study.domain.board.QLink;
import com.olivejua.study.domain.board.QPlaceRecommendation;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.olivejua.study.domain.QComment.*;
import static com.olivejua.study.domain.QUser.*;
import static com.olivejua.study.domain.board.QLikeHistory.*;
import static com.olivejua.study.domain.board.QLink.*;
import static com.olivejua.study.domain.board.QPlaceRecommendation.*;
import static com.querydsl.jpa.JPAExpressions.*;

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

    private BooleanExpression idEq(Long postId) {
        return placeRecommendation.id.eq(postId);
    }
}
