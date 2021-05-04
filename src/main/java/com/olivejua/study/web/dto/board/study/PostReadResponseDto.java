package com.olivejua.study.web.dto.board.study;

import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.domain.board.TechStack;
import com.olivejua.study.web.dto.comment.CommentReadResponseDto;
import com.olivejua.study.web.dto.user.WriterReadDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostReadResponseDto {
    private Long postId;
    private String title;
    private WriterReadDto writer;
    private List<String> techStack;
    private String place;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int capacity;
    private String explanation;
    private LocalDateTime createdDate;
    private List<CommentReadResponseDto> comments;

    public PostReadResponseDto(StudyRecruitment entity) {
        this.postId = entity.getId();
        this.title = entity.getTitle();

        this.writer = new WriterReadDto(entity.getWriter());

        this.techStack = entity.getTechStack().stream()
                            .map(TechStack::getElement)
                            .collect(Collectors.toList());

        //Condition
        this.place = entity.getCondition().getPlace();
        this.startDate = entity.getCondition().getStartDate();
        this.endDate = entity.getCondition().getEndDate();
        this.capacity = entity.getCondition().getCapacity();
        this.explanation = entity.getCondition().getExplanation();
        this.createdDate = entity.getCreatedDate();

        this.comments = entity.getComment().stream()
                            .map(CommentReadResponseDto::new)
                            .collect(Collectors.toList());
    }
}
