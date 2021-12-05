package com.olivejua.study.integration.mockFactory;

import com.olivejua.study.domain.studyRecruitment.Condition;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.common.mockData.MockStudyRecruitment;
import com.olivejua.study.repository.StudyRecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.time.LocalDate;
import java.util.List;

@TestComponent
public class StudyRecruitmentFactory {
    @Autowired
    private StudyRecruitmentRepository studyRecruitmentRepository;

    private static final String MOCK_TITLE = "Let's study together!";
    private static final List<String> MOCK_TECHS = List.of("Java", "Spring Boot", "JPA");
    private static final Condition MOCK_CONDITION =
            Condition.builder()
            .meetingPlace("any cafes in Gangnam")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusMonths(6))
            .capacity(10)
            .explanation(":)")
            .build();

    /****************************************************************************
     * Create a Post
     ***************************************************************************/
    public StudyRecruitment post(User author) {
        StudyRecruitment post = MockStudyRecruitment.builder()
                .author(author)
                .title(MOCK_TITLE)
                .techs(MOCK_TECHS)
                .condition(MOCK_CONDITION)
                .build();

        return studyRecruitmentRepository.save(post);
    }

    public StudyRecruitment post(User author, List<String> techs) {
        StudyRecruitment post = MockStudyRecruitment.builder()
                .author(author)
                .title(MOCK_TITLE)
                .techs(techs)
                .condition(MOCK_CONDITION)
                .build();

        return studyRecruitmentRepository.save(post);
    }
}
