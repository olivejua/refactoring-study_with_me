package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("recruit")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class StudyRecruitment extends Board {

    @OneToMany(mappedBy = "post")
    private List<TechStack> techStack = new ArrayList<>();

    @Embedded
    private Condition condition;


    /**
     * 글 작성
     */
    public static StudyRecruitment savePost(User writer, String title,
                                            List<String> techStacks, Condition condition) {

        StudyRecruitment newPost = new StudyRecruitment();
        newPost.createPost(writer, title);
        newPost.changeTechStacks(techStacks);
        newPost.condition = condition;

        return newPost;
    }

    /**
     * 글 수정
     */
    public void edit(String title, Condition condition, List<String> techStack) {
        editTitle(title);
        this.condition = condition;
        changeTechStacks(techStack);
    }

    /**
     * 기술스택 변경
     */
    private void changeTechStacks(List<String> techStack) {
        this.techStack = TechStack.createTechStacks(this, techStack);
    }
}
