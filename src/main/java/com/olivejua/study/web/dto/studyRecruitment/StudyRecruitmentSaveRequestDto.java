package com.olivejua.study.web.dto.studyRecruitment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StudyRecruitmentSaveRequestDto {
    private String title;
    private List<String> techs = new ArrayList<>();
    private String meetingPlace;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;
    private String explanation;

    @Builder
    public StudyRecruitmentSaveRequestDto(String title, List<String> techs, String meetingPlace, LocalDate startDate, LocalDate endDate, int capacity, String explanation) {
        this.title = title;
        this.techs = techs;
        this.meetingPlace = meetingPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.explanation = explanation;
    }
}
