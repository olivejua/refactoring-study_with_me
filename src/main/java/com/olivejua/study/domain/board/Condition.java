package com.olivejua.study.domain.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Condition {

    @OneToMany(mappedBy = "post")
    private List<Language> languages;
    private String place;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int capacity;
    private String explanation;

    @Builder
    public Condition(List<Language> languages, String place, LocalDateTime startDate,
                     LocalDateTime endDate, int capacity, String explanation) {
        this.languages = languages;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.explanation = explanation;
    }
}
