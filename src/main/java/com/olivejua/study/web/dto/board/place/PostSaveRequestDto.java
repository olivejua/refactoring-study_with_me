package com.olivejua.study.web.dto.board.place;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String address;
    private String addressDetail;
    private List<String> links;
    private String thumbnailPath;
    private String content;

    public PostSaveRequestDto(String title, String address, String addressDetail,
                              List<String> links, String thumbnailPath, String content) {
        this.title = title;
        this.address = address;
        this.addressDetail = addressDetail;
        this.links = links;
        this.thumbnailPath = thumbnailPath;
        this.content = content;
    }
}
