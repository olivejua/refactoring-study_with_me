package com.olivejua.study.domain;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("place")
@NoArgsConstructor(access = PROTECTED)
public class PlaceRecommendation extends Post {

    private String address;
    private String addressDetail;
    private String content;

    private final Links links = new Links();

    public PlaceRecommendation(User author, String title, String address,
                               String addressDetail, String content) {
        super(author, title);
        this.address = address;
        this.addressDetail = addressDetail;
        this.content = content;
    }

    /**
     * 글 작성
     */
    public static PlaceRecommendation createPost(User author, String title,
                                                 String address, String addressDetail,
                                                 String content, List<String> links) {

        PlaceRecommendation post = new PlaceRecommendation(author, title, address, addressDetail, content);
        post.replaceLinks(links);

        return post;
    }

    /**
     * 글 수정
     */
    public void update(String title, String address, String addressDetail,
                       String content, List<String> links) {
        updateTitle(title);
        this.address = address;
        this.addressDetail = addressDetail;
        this.content = content;
        replaceLinks(links);
    }

    /**
     * 링크 목록 변경
     */
    private void replaceLinks(List<String> links) {
        this.links.replace(this, links);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, addressDetail, content, links, likes);
    }
}
