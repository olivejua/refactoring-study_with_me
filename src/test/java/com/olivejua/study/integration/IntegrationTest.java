package com.olivejua.study.integration;

import com.olivejua.study.integration.mockFactory.factory.StudyRecruitmentFactory;
import com.olivejua.study.integration.mockFactory.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Import({
        UserFactory.class,
        StudyRecruitmentFactory.class
})
@Transactional
@SpringBootTest
public class IntegrationTest {

    @Autowired
    protected UserFactory userFactory;

    @Autowired
    protected StudyRecruitmentFactory studyRecruitmentFactory;
}
