package com.olivejua.study.domain.placeRecommendation;

import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.user.User;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
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

    @Embedded
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

    public static PlaceRecommendation createPost(Long id, User author, String title,
                                                 String address, String addressDetail,
                                                 String content, List<String> links) {

        PlaceRecommendation post = createPost(author, title, address, addressDetail, content, links);
        post.id = id;

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

    /**
     * Getter
     */
    public int getSizeOfLinks() {
        return links.size();
    }

    public List<Link> getLinks() {
        return links.getLinks();
    }

    public List<String> getElementsOfLinks() {
        return links.getElements();
    }

    public String getAddress() {
        return address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getContent() {
        return content;
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
