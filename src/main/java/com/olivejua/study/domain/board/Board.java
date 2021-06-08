package com.olivejua.study.domain.board;

import com.olivejua.study.domain.BaseTimeEntity;
import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import lombok.Getter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Getter
public abstract class Board extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    @Column(nullable = false)
    private String title;

    private int viewCount;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment = new ArrayList<>();

    /**
     * 새 게시글 작성
     */
    public void createPost(User writer, String title) {
        this.title = title;
        this.writer = writer;
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
    protected void editTitle(String title) {
        this.title = title;
    }
}
