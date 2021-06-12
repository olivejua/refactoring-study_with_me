package com.olivejua.study.domain.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Condition {
    private String place;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int capacity;

    private String explanation;

    private Condition(String place, LocalDateTime startDate, LocalDateTime endDate, int capacity, String explanation) {
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.explanation = explanation;
    }

    public static Condition createCondition(String place, LocalDateTime startDate,
                                            LocalDateTime endDate, int capacity, String explanation) {
        return new Condition(
                place, startDate, endDate, capacity, explanation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return capacity == condition.capacity && Objects.equals(place, condition.place) && Objects.equals(startDate, condition.startDate) && Objects.equals(endDate, condition.endDate) && Objects.equals(explanation, condition.explanation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place, startDate, endDate, capacity, explanation);
    }
}
