package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("recruit")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class StudyRecruitment extends Board {

    @Embedded
    @Column(name = "CONDITION")
    private Condition condition;

    @OneToMany(mappedBy = "board")
    private List<Comment> comment = new ArrayList<>();

    @Builder
    public StudyRecruitment(User user, String title, Condition condition) {
        createPost(user, title);
        this.condition = condition;
    }

    /**
     * 글 수정
     */
    public void edit(String title, Condition condition) {
        editTitle(title);
        this.condition = condition;
    }
}
