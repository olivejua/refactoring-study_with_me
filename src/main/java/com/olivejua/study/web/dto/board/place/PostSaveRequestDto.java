package com.olivejua.study.web.dto.board.place;

import lombok.Getter;

import java.util.List;

@Getter
public class PostSaveRequestDto {
    private String title;
    private String address;
    private String addressDetail;
    private List<String> links;
    private String thumbnailPath;
    private String content;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;

    public PostSaveRequestDto(String title, String address, String addressDetail,
                              List<String> links, String thumbnailPath, String content,
                              int likeCount, int dislikeCount, int viewCount) {
        this.title = title;
        this.address = address;
        this.addressDetail = addressDetail;
        this.links = links;
        this.thumbnailPath = thumbnailPath;
        this.content = content;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.viewCount = viewCount;
    }
}
