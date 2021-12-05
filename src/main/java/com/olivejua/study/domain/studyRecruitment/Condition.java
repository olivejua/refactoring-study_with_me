package com.olivejua.study.domain.studyRecruitment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Condition {
    private String meetingPlace;

    private LocalDate startDate;

    private LocalDate endDate;

    private int capacity;

    private String explanation;

    @Builder
    private Condition(String meetingPlace, LocalDate startDate, LocalDate endDate, int capacity, String explanation) {
        this.meetingPlace = meetingPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.explanation = explanation;
    }

    /**
     * equals and hashcode
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return capacity == condition.capacity && Objects.equals(meetingPlace, condition.meetingPlace) && Objects.equals(startDate, condition.startDate) && Objects.equals(endDate, condition.endDate) && Objects.equals(explanation, condition.explanation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingPlace, startDate, endDate, capacity, explanation);
    }
}
