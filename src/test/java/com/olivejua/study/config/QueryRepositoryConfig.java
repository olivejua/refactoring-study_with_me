package com.olivejua.study.config;

import com.olivejua.study.unit.repository.board.PlaceRecommendationQueryRepository;
import com.olivejua.study.unit.repository.board.QuestionQueryRepository;
import com.olivejua.study.unit.repository.board.StudyRecruitmentQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(QuerydslConfig.class)
public class QueryRepositoryConfig {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Bean
    public QuestionQueryRepository questionQueryRepository() {
        return new QuestionQueryRepository(jpaQueryFactory);
    }

    @Bean
    public PlaceRecommendationQueryRepository placeRecommendationQueryRepository() {
        return new PlaceRecommendationQueryRepository(jpaQueryFactory);
    }

    @Bean
    public StudyRecruitmentQueryRepository studyRecruitmentQueryRepository() {
        return new StudyRecruitmentQueryRepository(jpaQueryFactory);
    }
}
