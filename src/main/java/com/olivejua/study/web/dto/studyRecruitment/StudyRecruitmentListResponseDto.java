package com.olivejua.study.web.dto.studyRecruitment;

import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class StudyRecruitmentListResponseDto {
    private Long id;
    private AuthorResponseDto author;
    private String title;
    private final List<String> techs = new ArrayList<>();
    private LocalDateTime createdDate;

    public StudyRecruitmentListResponseDto(StudyRecruitment post) {
        id = post.getId();
        author = new AuthorResponseDto(post.getAuthor());
        title = post.getTitle();
        techs.addAll(post.getElementsOfTechs());
        createdDate = post.getCreatedDate();
    }

    @NoArgsConstructor(access = PROTECTED)
    @Getter
    private static class AuthorResponseDto {
        private Long id;
        private String name;

        public AuthorResponseDto(User user) {
            id = user.getId();
            name = user.getName();
        }
    }
}
