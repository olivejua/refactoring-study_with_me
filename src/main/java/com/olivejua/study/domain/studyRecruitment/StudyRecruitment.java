package com.olivejua.study.domain.studyRecruitment;

import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.user.User;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("recruit")
@NoArgsConstructor(access = PROTECTED)
public class StudyRecruitment extends Post {

    @Embedded
    private final Techs techs = new Techs();

    @Embedded
    private Condition condition;


    public StudyRecruitment(User author, String title, Condition condition) {
        super(author, title);
        this.condition = condition;
    }

    public StudyRecruitment(Long id, User author, String title, Condition condition) {
        super(id, author, title);
        this.condition = condition;
    }

    /**
     * 글 작성
     */
    public static StudyRecruitment createPost(User author, String title,
                                              List<String> tech, Condition condition) {

        StudyRecruitment newPost = new StudyRecruitment(author, title, condition);
        newPost.replaceTech(tech);

        return newPost;
    }

    public static StudyRecruitment createPost(Long id, User author, String title,
                                              List<String> tech, Condition condition) {

        StudyRecruitment newPost = new StudyRecruitment(id, author, title, condition);
        newPost.replaceTech(tech);

        return newPost;
    }

    /**
     * 글 수정
     */
    public void update(String title, Condition condition, List<String> techStack) {
        updateTitle(title);
        this.condition = condition;
        replaceTech(techStack);
    }

    /**
     * 기술스택
     */
    private void replaceTech(List<String> techs) {
        this.techs.replace(this, techs);
    }

    public boolean containsTech(List<String> techs) {
        return this.techs.containsAll(techs);
    }

    public int getSizeOfTechs() {
        return this.techs.size();
    }
}
