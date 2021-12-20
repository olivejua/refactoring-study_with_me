package com.olivejua.study.repository.query;

import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import com.olivejua.study.domain.placeRecommendation.QLink;
import com.olivejua.study.domain.placeRecommendation.QPlaceRecommendation;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
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

import static com.olivejua.study.domain.placeRecommendation.QLink.*;
import static com.olivejua.study.domain.placeRecommendation.QPlaceRecommendation.*;
import static com.olivejua.study.domain.studyRecruitment.QStudyRecruitment.studyRecruitment;
import static com.olivejua.study.domain.studyRecruitment.QTech.tech;
import static com.olivejua.study.domain.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class PlaceRecommendationRepositoryImpl implements PlaceRecommendationQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PlaceRecommendation> findPosts(Pageable pageable) {
        return null;
    }

    @Override
    public Page<StudyRecruitment> findPosts(Pageable pageable) {
        JPAQuery<StudyRecruitment> query = getPostsSelectQuery();

        List<StudyRecruitment> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(createdDateDesc())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }

    private JPAQuery<StudyRecruitment> getPostsSelectQuery() {
        return queryFactory
                .selectFrom(placeRecommendation)
                .join(placeRecommendation.author, user).fetchJoin()
                .leftJoin(placeRecommendation.links.links, link).fetchJoin()
                .leftJoin()
                .distinct();
    }

    private OrderSpecifier<LocalDateTime> createdDateDesc() {
        return studyRecruitment.createdDate.desc();
    }
}
