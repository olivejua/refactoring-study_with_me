package com.olivejua.study.repository.query;

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

import static com.olivejua.study.domain.studyRecruitment.QStudyRecruitment.studyRecruitment;
import static com.olivejua.study.domain.studyRecruitment.QTech.tech;
import static com.olivejua.study.domain.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class StudyRecruitmentQueryRepositoryImpl implements StudyRecruitmentQueryRepository {
    private final JPAQueryFactory queryFactory;

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
                .selectFrom(studyRecruitment)
                .join(studyRecruitment.author, user).fetchJoin()
                .leftJoin(studyRecruitment.techs.techs, tech).fetchJoin()
                .distinct();
    }

    private OrderSpecifier<LocalDateTime> createdDateDesc() {
        return studyRecruitment.createdDate.desc();
    }
}
