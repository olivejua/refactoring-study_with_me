package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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
    public static StudyRecruitment createPost(User writer, String title,
                                              List<String> techStack, Condition condition) {

        StudyRecruitment newPost = new StudyRecruitment();
        newPost.createPost(writer, title);
        newPost.replaceTechStack(techStack);
        newPost.condition = condition;

        return newPost;
    }

    /**
     * 글 수정
     */
    public void update(String title, Condition condition, List<String> techStack) {
        editTitle(title);
        this.condition = condition;
        replaceTechStack(techStack);
    }

    /**
     * 기술스택 변경
     */
    private void replaceTechStack(List<String> techStack) {
        this.techStack.clear();
        techStack.forEach(e -> {
            TechStack newTech = TechStack.createTechStack(this, e);
            this.techStack.add(newTech);
        });
    }
}
