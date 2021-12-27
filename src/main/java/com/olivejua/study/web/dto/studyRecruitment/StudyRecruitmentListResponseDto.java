package com.olivejua.study.web.dto.studyRecruitment;

import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.web.dto.post.PostListResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class StudyRecruitmentListResponseDto extends PostListResponseDto {
    private final List<String> techs = new ArrayList<>();

    public StudyRecruitmentListResponseDto(StudyRecruitment post) {
        super(post);
        techs.addAll(post.getElementsOfTechs());
    }
}
