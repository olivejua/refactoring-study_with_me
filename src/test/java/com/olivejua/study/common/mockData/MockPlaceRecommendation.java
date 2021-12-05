package com.olivejua.study.common.mockData;

import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import com.olivejua.study.domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class MockPlaceRecommendation {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User author;
        private String title;
        private String address;
        private String addressDetail;
        private String content;
        private List<String> links = new ArrayList<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder author(User author) {
            this.author = author;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder addressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder links(List<String> links) {
            this.links.addAll(links);
            return this;
        }

        public PlaceRecommendation build() {
            return PlaceRecommendation.createPost(
                    id,
                    author,
                    title,
                    address,
                    addressDetail,
                    content,
                    links
            );
        }
    }
}
