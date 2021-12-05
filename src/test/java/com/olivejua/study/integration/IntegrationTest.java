package com.olivejua.study.integration;

import com.olivejua.study.integration.mockFactory.PlaceRecommendationFactory;
import com.olivejua.study.integration.mockFactory.QuestionFactory;
import com.olivejua.study.integration.mockFactory.StudyRecruitmentFactory;
import com.olivejua.study.integration.mockFactory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Import({
        UserFactory.class,
        StudyRecruitmentFactory.class,
        PlaceRecommendationFactory.class,
        QuestionFactory.class
})
@Transactional
@SpringBootTest
public class IntegrationTest {

    @Autowired
    protected UserFactory userFactory;

    @Autowired
    protected StudyRecruitmentFactory studyRecruitmentFactory;

    @Autowired
    protected PlaceRecommendationFactory placeRecommendationFactory;

    @Autowired
    protected QuestionFactory questionFactory;
}
