package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
    private List<TechStack> techStack;

    @Embedded
    private Condition condition;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment = new ArrayList<>();


    public static StudyRecruitment savePost(User writer, String title,
                                            List<String> languages, Condition condition) {

        StudyRecruitment newPost = new StudyRecruitment();
        newPost.createPost(writer, title);
        newPost.changeTechStacks(languages);
        newPost.condition = condition;

        return newPost;
    }

    /**
     * 기술스택 변경
     */
    private void changeTechStacks(List<String> techStack) {
        this.techStack = techStack.stream()
                            .map(ts -> new TechStack(this, ts))
                            .collect(Collectors.toList());
    }

    /**
     * 글 수정
     */
    public void edit(String title, Condition condition, List<String> techStack) {
        editTitle(title);
        this.condition = condition;
        changeTechStacks(techStack);
    }
}
