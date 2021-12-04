package com.olivejua.study.web.dto.board.study;

import com.olivejua.study.domain.Condition;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private List<String> techStack;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;
    private String explanation;

    public PostSaveRequestDto(String title, List<String> techStack, String place,
                              LocalDate startDate, LocalDate endDate,
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
                .meetingPlace(place)
                .startDate(startDate)
                .endDate(endDate)
                .capacity(capacity)
                .explanation(explanation)
                .build();
    }
}
