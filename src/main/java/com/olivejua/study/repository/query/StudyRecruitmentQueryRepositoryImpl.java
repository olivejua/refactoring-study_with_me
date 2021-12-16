package com.olivejua.study.repository.query;

import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

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
        JPAQuery<StudyRecruitment> query = queryFactory
                .selectFrom(studyRecruitment)
                .join(studyRecruitment.author, user)
                .leftJoin(studyRecruitment.techs.techs, tech);

        List<StudyRecruitment> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(studyRecruitment.createdDate.desc())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }
}
