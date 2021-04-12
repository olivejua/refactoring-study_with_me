package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("place")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PlaceRecommendation extends Board {

    private String address;
    private String addressDetail;
    private String thumbnailPath;
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Link> links = new ArrayList<>();

    @Builder
    public PlaceRecommendation(User writer, String title,
                               String address, String addressDetail,
                               String thumbnailPath, String content, List<Link> links) {
        createPost(writer, title);
        this.address = address;
        this.addressDetail = addressDetail;
        this.thumbnailPath = thumbnailPath;
        this.content = content;
        this.links = links;
    }

    /**
     * 글 수정
     */
    public void edit(String title, String address, String addressDetail,
                     String thumbnailPath, String content, List<Link> links) {
        editTitle(title);
        this.address = address;
        this.addressDetail = addressDetail;
        this.thumbnailPath = thumbnailPath;
        this.content = content;
        this.links = links;
    }
}
