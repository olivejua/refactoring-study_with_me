package com.olivejua.study.web.dto.studyRecruitment;

import com.olivejua.study.domain.studyRecruitment.Condition;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class StudyRecruitmentReadResponseDto {
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
    private List<CommentResponseDto> comments;

    public StudyRecruitmentReadResponseDto(StudyRecruitment entity) {
        id = entity.getId();
        initAuthor(entity.getAuthor());
        title = entity.getTitle();
        techs.addAll(entity.getElementsOfTechs());
        initCondition(entity.getCondition());
        createdDate = entity.getCreatedDate();
    }

    @QueryProjection
    public StudyRecruitmentReadResponseDto(Long id, User author, String title, String meetingPlace, LocalDate startDate, LocalDate endDate, int capacity, String explanation, LocalDateTime createdDate) {
        this.id = id;
        initAuthor(author);
        this.title = title;
        this.meetingPlace = meetingPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.explanation = explanation;
        this.createdDate = createdDate;
    }

    private void initAuthor(User author) {
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

    @NoArgsConstructor(access = PROTECTED)
    @Getter
    private static class CommentResponseDto {
        private Long id;
        private String content;
        private AuthorResponseDto author;
    }
}