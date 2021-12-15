package com.olivejua.study.web.dto.studyRecruitment;

import com.olivejua.study.domain.studyRecruitment.Condition;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class StudyRecruitmentResponseDto {
    private Long id;
    private AuthorResponseDto author;
    private String title;
    private final List<String> techs = new ArrayList<>();
    private String meetingPlace;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;
    private String explanation;
    private LocalDateTime createdDate;

    public StudyRecruitmentResponseDto(StudyRecruitment post) {
        id = post.getId();
        initAuthor(post.getAuthor());
        title = post.getTitle();
        techs.addAll(post.getElementsOfTechs());
        initCondition(post.getCondition());
        createdDate = post.getCreatedDate();
    }

    public void initAuthor(User author) {
        this.author = new AuthorResponseDto(author);
    }

    public void initCondition(Condition condition) {
        meetingPlace = condition.getMeetingPlace();
        startDate = condition.getStartDate();
        endDate = condition.getEndDate();
        capacity = condition.getCapacity();
        explanation = condition.getExplanation();
    }

    @NoArgsConstructor(access = PROTECTED)
    @Getter
    private static class AuthorResponseDto {
        private Long id;
        private String name;

        public AuthorResponseDto(User user) {
            id = user.getId();
            name = user.getName();
        }
    }
}


