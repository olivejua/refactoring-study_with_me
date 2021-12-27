package com.olivejua.study.repository.query;

import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.olivejua.study.domain.placeRecommendation.QPlaceRecommendation.placeRecommendation;
import static com.olivejua.study.domain.studyRecruitment.QStudyRecruitment.studyRecruitment;
import static com.olivejua.study.domain.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class PlaceRecommendationRepositoryImpl implements PlaceRecommendationQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PlaceRecommendation> findPosts(Pageable pageable) {
        JPAQuery<PlaceRecommendation> query = getPostsSelectQuery();

        List<PlaceRecommendation> content = query
                .orderBy(createdDateDesc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }

    private JPAQuery<PlaceRecommendation> getPostsSelectQuery() {
        return queryFactory
                .selectFrom(placeRecommendation)
                .join(placeRecommendation.author, user).fetchJoin()
                .distinct();
    }

    private OrderSpecifier<LocalDateTime> createdDateDesc() {
        return studyRecruitment.createdDate.desc();
    }
}
