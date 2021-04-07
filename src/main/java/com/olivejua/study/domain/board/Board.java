package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Getter
public abstract class Board {

    @Id @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "VIEW_COUNT")
    private int viewCount;

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
