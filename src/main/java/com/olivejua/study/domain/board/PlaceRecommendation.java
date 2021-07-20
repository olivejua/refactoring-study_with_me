package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("place")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PlaceRecommendation extends Board {

    private String address;
    private String addressDetail;
    private String thumbnailName;
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<LikeHistory> likes = new ArrayList<>();

    /**
     * 글 작성
     */
    public static PlaceRecommendation savePost(User writer, String title,
                                               String address, String addressDetail,
                                               String thumbnailPath, String content, List<String> links) {

        PlaceRecommendation newPost = new PlaceRecommendation();
        newPost.createPost(writer, title);
        newPost.replaceLinks(links);
        newPost.address = address;
        newPost.addressDetail = addressDetail;
        newPost.thumbnailName = newPost.makeThumbnailNameUnique(thumbnailPath);
        newPost.content = content;

        return newPost;
    }

    /**
     * 글 수정
     */
    public void edit(String title, String address, String addressDetail,
                     String thumbnailName, String content, List<String> links) {
        editTitle(title);
        this.address = address;
        this.addressDetail = addressDetail;
        this.thumbnailName = thumbnailName;
        this.content = content;
        replaceLinks(links);
    }

    /**
     * 링크 목록 변경
     */
    private void replaceLinks(List<String> links) {
        this.links.clear();
        links.forEach(e -> {
            Link newLink = Link.createLink(this, e);
            this.links.add(newLink);
        });
    }

    /**
     * 썸네일 업데이트
     */
    private String makeThumbnailNameUnique(String thumbnailName) {
        return UUID.randomUUID()+thumbnailName;
    }
}
