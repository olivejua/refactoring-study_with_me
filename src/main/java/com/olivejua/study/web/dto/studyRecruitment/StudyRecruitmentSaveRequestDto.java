package com.olivejua.study.web.dto.studyRecruitment;

import com.olivejua.study.domain.studyRecruitment.Condition;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StudyRecruitmentSaveRequestDto {
    @NotBlank(message = "게시글 제목이 비어있지 않아야 합니다")
    private String title;
    private final List<String> techs = new ArrayList<>();
    private String meetingPlace;
    private LocalDate startDate;
    private LocalDate endDate;
    @Positive(message = "최대 인원수는 양수여야 합니다")
    private int capacity;
    private String explanation;
    private final List<MultipartFile> images = new ArrayList<>();

    @Builder
    public StudyRecruitmentSaveRequestDto(String title, List<String> techs, String meetingPlace,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startDate,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate,
                                          int capacity, String explanation, List<MultipartFile> images) {
        this.title = title;
        addTechsAll(techs);
        this.meetingPlace = meetingPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.explanation = explanation;
        addImagesAll(images);
    }

    private void addTechsAll(List<String> techs) {
        if (techs==null) return;
        this.techs.addAll(techs);
    }

    private void addImagesAll(List<MultipartFile> images) {
        if (images==null) return;
        this.images.addAll(images);
    }

    public StudyRecruitment toEntity(User author) {
        return StudyRecruitment.createPost(
                author,
                title,
                techs,
                toCondition()
        );
    }

    private Condition toCondition() {
        return Condition.builder()
                .meetingPlace(meetingPlace)
                .startDate(startDate)
                .endDate(endDate)
                .capacity(capacity)
                .explanation(explanation)
                .build();
    }
}
