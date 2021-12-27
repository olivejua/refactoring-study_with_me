package com.olivejua.study.web.dto.studyRecruitment;

import com.olivejua.study.domain.studyRecruitment.Condition;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.web.dto.post.PostReadResponseDto;
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
public class StudyRecruitmentReadResponseDto extends PostReadResponseDto {
    private final List<String> techs = new ArrayList<>();
    private String meetingPlace;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;
    private String explanation;

    public StudyRecruitmentReadResponseDto(StudyRecruitment entity) {
        super(entity);
        techs.addAll(entity.getElementsOfTechs());
        initCondition(entity.getCondition());
    }

    @QueryProjection
    public StudyRecruitmentReadResponseDto(Long id, User author, String title, String meetingPlace, LocalDate startDate, LocalDate endDate, int capacity, String explanation, LocalDateTime createdDate) {
        super(id, author, title, createdDate);
        this.meetingPlace = meetingPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.explanation = explanation;

    }

    public void initCondition(Condition condition) {
        meetingPlace = condition.getMeetingPlace();
        startDate = condition.getStartDate();
        endDate = condition.getEndDate();
        capacity = condition.getCapacity();
        explanation = condition.getExplanation();
    }
}