package com.olivejua.study.web.dto.placeRecommendation;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlaceRecommendationUpdateRequestDto {
    private String title;
    private String address;
    private String addressDetail;
    private final List<String> links = new ArrayList<>();
    private String content;
    private final List<MultipartFile> images = new ArrayList<>();

    @Builder
    public PlaceRecommendationUpdateRequestDto(String title, String address, String addressDetail,
                                             List<String> links, String content, List<MultipartFile> images) {
        this.title = title;
        this.address = address;
        this.addressDetail = addressDetail;
        addLinksAll(links);
        this.content = content;
        addImagesAll(images);
    }

    private void addLinksAll(List<String> links) {
        if (links==null) return;
        this.links.addAll(links);
    }

    private void addImagesAll(List<MultipartFile> images) {
        if (images==null) return;
        this.images.addAll(images);
    }
}
