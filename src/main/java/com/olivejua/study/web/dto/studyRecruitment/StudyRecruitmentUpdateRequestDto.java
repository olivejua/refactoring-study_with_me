package com.olivejua.study.web.dto.studyRecruitment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.olivejua.study.domain.studyRecruitment.Condition;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StudyRecruitmentUpdateRequestDto {
    @NotBlank(message = "게시글 제목이 비어있지 않아야 합니다")
    private String title;
    private final List<String> techs = new ArrayList<>();
    private String meetingPlace;
    private LocalDate startDate;
    private LocalDate endDate;
    @Positive(message = "최대 인원수는 양수여야 합니다")
    private int capacity;
    private String explanation;

    @Builder
    public StudyRecruitmentUpdateRequestDto(String title, List<String> techs, String meetingPlace, LocalDate startDate, LocalDate endDate, int capacity, String explanation) {
        this.title = title;
        this.techs.addAll(techs);
        this.meetingPlace = meetingPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.explanation = explanation;
    }

    public Condition toCondition() {
        return Condition.builder()
                .meetingPlace(meetingPlace)
                .startDate(startDate)
                .endDate(endDate)
                .capacity(capacity)
                .explanation(explanation)
                .build();
    }
}
