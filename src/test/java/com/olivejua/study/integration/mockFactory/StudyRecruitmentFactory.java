package com.olivejua.study.integration.mockFactory;

import com.olivejua.study.common.mockData.MockStudyRecruitment;
import com.olivejua.study.domain.studyRecruitment.Condition;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.repository.StudyRecruitmentRepository;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.web.multipart.MultipartFile;

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
     * Create an Entity
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

    public StudyRecruitment post(User author, String title) {
        StudyRecruitment post = MockStudyRecruitment.builder()
                .author(author)
                .title(title)
                .techs(MOCK_TECHS)
                .condition(MOCK_CONDITION)
                .build();

        return studyRecruitmentRepository.save(post);
    }

    public StudyRecruitment postWithImages(User author, List<String> images) {
        StudyRecruitment post = MockStudyRecruitment.builder()
                .author(author)
                .title(MOCK_TITLE)
                .techs(MOCK_TECHS)
                .condition(MOCK_CONDITION)
                .build();
        post.addImages(images);

        return studyRecruitmentRepository.save(post);
    }

    /****************************************************************************
     * Create a SaveRequestDto
     ***************************************************************************/

    public StudyRecruitmentSaveRequestDto saveRequestDto() {
        return StudyRecruitmentSaveRequestDto.builder()
                .title("test title")
                .techs(List.of("java", "jpa", "spring boot"))
                .meetingPlace("강남")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .capacity(10)
                .explanation("test explanation")
                .build();
    }

    public StudyRecruitmentSaveRequestDto saveRequestDto(String title, int capacity) {
        return StudyRecruitmentSaveRequestDto.builder()
                .title(title)
                .techs(List.of("java", "jpa", "spring boot"))
                .meetingPlace("강남")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .capacity(capacity)
                .explanation("test explanation")
                .build();
    }

    public StudyRecruitmentSaveRequestDto saveRequestDto(List<MultipartFile> images) {
        return StudyRecruitmentSaveRequestDto.builder()
                .title("test title")
                .techs(List.of("java", "jpa", "spring boot"))
                .meetingPlace("강남")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .capacity(10)
                .explanation("test explanation")
                .images(images)
                .build();
    }

    /****************************************************************************
     * Create a UpdateRequestDto
     ***************************************************************************/

    public StudyRecruitmentUpdateRequestDto updateRequestDto() {
        return StudyRecruitmentUpdateRequestDto.builder()
                .title("updated title")
                .techs(List.of("node.js", "typescript"))
                .meetingPlace("홍대")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .capacity(5)
                .explanation("updated explanation")
                .build();
    }

    public StudyRecruitmentUpdateRequestDto updateRequestDto(List<MultipartFile> images) {
        return StudyRecruitmentUpdateRequestDto.builder()
                .title("updated title")
                .techs(List.of("node.js", "typescript"))
                .meetingPlace("홍대")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .capacity(5)
                .explanation("updated explanation")
                .images(images)
                .build();
    }

}
