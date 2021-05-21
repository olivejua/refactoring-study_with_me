package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "post")
    private List<LikeHistory> likes;

    /**
     * 글 작성
     */
    public static PlaceRecommendation savePost(User writer, String title,
                                               String address, String addressDetail,
                                               String thumbnailPath, String content, List<String> links) {

        PlaceRecommendation newPost = new PlaceRecommendation();
        newPost.createPost(writer, title);
        newPost.changeLinks(links);
        newPost.address = address;
        newPost.addressDetail = addressDetail;
        newPost.thumbnailPath = thumbnailPath;
        newPost.content = content;

        return newPost;
    }

    /**
     * 글 수정
     */
    public void edit(String title, String address, String addressDetail,
                     String thumbnailPath, String content, List<String> links) {
        editTitle(title);
        this.address = address;
        this.addressDetail = addressDetail;
        this.thumbnailPath = thumbnailPath;
        this.content = content;
        changeLinks(links);
    }

    /**
     * 링크 목록 변경
     */
    private void changeLinks(List<String> links) {
        this.links = links.stream()
                .map(link -> new Link(this, link))
                .collect(Collectors.toList());
    }
}
