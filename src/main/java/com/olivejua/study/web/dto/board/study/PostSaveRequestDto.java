package com.olivejua.study.web.dto.board.study;

import com.olivejua.study.domain.board.Condition;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostSaveRequestDto {
    private String title;
    private List<String> techStack;
    private String place;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int capacity;
    private String explanation;

    @Builder
    public PostSaveRequestDto(String title, List<String> techStack, String place,
                              LocalDateTime startDate, LocalDateTime endDate,
                              int capacity, String explanation) {
        this.title = title;
        this.techStack = techStack;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.explanation = explanation;
    }

    public Condition getCondition() {
        return Condition.builder()
                .place(place)
                .startDate(startDate)
                .endDate(endDate)
                .capacity(capacity)
                .explanation(explanation)
                .build();
    }
}
