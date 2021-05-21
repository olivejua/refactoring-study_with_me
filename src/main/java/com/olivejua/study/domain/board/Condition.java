package com.olivejua.study.domain.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

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
}
