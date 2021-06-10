package com.olivejua.study.web.dto.board.study;

import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.domain.board.TechStack;
import com.olivejua.study.web.dto.PageDto;
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
    private PageDto pageInfo;

    public PostReadResponseDto(StudyRecruitment entity) {
        postId = entity.getId();
        title = entity.getTitle();

        writer = new WriterReadDto(entity.getWriter());

        techStack = entity.getTechStack().stream()
                            .map(TechStack::getElement)
                            .collect(Collectors.toList());

        //Condition
        place = entity.getCondition().getPlace();
        startDate = entity.getCondition().getStartDate();
        endDate = entity.getCondition().getEndDate();
        capacity = entity.getCondition().getCapacity();
        explanation = entity.getCondition().getExplanation();
        createdDate = entity.getCreatedDate();

        comments = entity.getComment().stream()
                            .map(CommentReadResponseDto::new)
                            .collect(Collectors.toList());
    }

    public void savePageInfo(PageDto pageInfo) {
        this.pageInfo = pageInfo;
    }
}
