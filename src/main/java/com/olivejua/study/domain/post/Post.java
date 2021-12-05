package com.olivejua.study.domain.post;

import com.olivejua.study.domain.BaseTimeEntity;
import com.olivejua.study.domain.comment.Comment;
import com.olivejua.study.domain.comment.Comments;
import com.olivejua.study.domain.image.Images;
import com.olivejua.study.domain.like.Likes;
import com.olivejua.study.domain.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.*;

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "POST_ID")
    protected Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    protected User author;

    @Column(nullable = false, length = 100)
    protected String title;

    protected int viewCount;

    @Embedded
    protected Images images = new Images();

    @Embedded
    protected Comments comments = new Comments();

    @Embedded
    protected Likes likes = new Likes();

    /**
     * 새 게시글 작성
     */
    public Post() {}

    public Post(User author, String title) {
        this.title = title;
        this.author = author;
    }

    public Post(Long id, User author, String title) {
        this(author, title);
        this.id = id;
    }

    /**
     * 조회수 늘리기
     */
    public void addViewCount() {
        viewCount += 1;
    }

    /**
     * 글 제목 수정
     */
    protected void updateTitle(String title) {
        this.title = title;
    }

    /**
     * 첨부이미지 수정
     */
    protected void replaceImages(List<String> images) {
        this.images.addAll(images);
    }


    /**
     * 좋아요(like) 추가
     */
    public void hasBeenLikedBy(User user) {
        likes.addLike(this, user);
    }

    public void hasBeenDislikedBy(User user) {
        likes.addDislike(this, user);
    }

    /**
     * 댓글
     */
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    /**
     * Getter 
     */
    public Long getId() {
        return id;
    }

    public String getNameOfAuthor() {
        return author.getName();
    }

    public String getTitle() {
        return title;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getSizeOfLikes() {
        return likes.getSizeOfLikes();
    }

    public int getSizeOfDislikes() {
        return likes.getSizeOfDislikes();
    }

    public int getSizeOfComments() {
        return comments.size();
    }

    public Comments getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, viewCount, images, comments);
    }
}
