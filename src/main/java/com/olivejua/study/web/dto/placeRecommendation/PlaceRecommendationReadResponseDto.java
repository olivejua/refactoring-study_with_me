package com.olivejua.study.web.dto.placeRecommendation;

import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import com.olivejua.study.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

public class PlaceRecommendationReadResponseDto {
    private Long id;
    private AuthorResponseDto author;
    private String title;
    private final List<String> links = new ArrayList<>();
    private String address;
    private String addressDetail;
    private String content;
    private LocalDateTime createdDate;

    public PlaceRecommendationReadResponseDto(PlaceRecommendation entity) {
        id = entity.getId();
        initAuthor(entity.getAuthor());
        title = entity.getTitle();
        links.addAll(entity.getElementsOfLinks());
        address = entity.getAddress();
        addressDetail = entity.getAddressDetail();
        content = entity.getContent();
        createdDate = entity.getCreatedDate();
    }

    private void initAuthor(User author) {
        this.author = new AuthorResponseDto(author);
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
