package com.olivejua.study;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.Reply;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.domain.board.TechStack;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.repository.board.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitClass {

    private final InitDataService initDataService;

    @PostConstruct
    public void init() {
        initDataService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitDataService {
        private final EntityManager em;

        private final StudyRecruitmentRepository studyRecruitmentRepository;
        private final UserRepository userRepository;
        private final TechStackRepository techStackRepository;

        private List<User> users;
        private List<StudyRecruitment> studyPosts;

        @Transactional
        public void init() {
            // TODO User 생성
            users = getUsers();

            // TODO StudyRecruitment 생성 (+ TechStack 생성, Comment 생성, Reply 생성)
            studyPosts = getStudyPosts();

            // TODO PlaceRecommendation 생성 (+ Link 생성, Like 생성, Comment 생성, Reply 생성)


            // TODO Question 생성 (+ Comment 생성, Reply 생성)
        }

        private List<User> getUsers() {
            List<User> users = createUsers(200);
            users.forEach(em::persist);

            return users;
        }

        private List<StudyRecruitment> getStudyPosts() {
            SampleStudyRecruitment sample = new SampleStudyRecruitment();

            List<StudyRecruitment> posts = sample.createStudyPosts(100);

            for (StudyRecruitment post : posts) {
                persistStudy(post);

                List<Comment> comments = createComments(post, 3);

                comments.forEach(this::persistComment);
            }

            return posts;
        }

        private void persistStudy(StudyRecruitment post) {
            em.persist(post);
            post.getTechStack().forEach(em::persist);
        }

        private void persistComment(Comment comment) {
            em.persist(comment);
            comment.getReplies().forEach(em::persist);
        }

        private List<User> createUsers(int size) {
            List<User> results = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                results.add(User.createUser(
                        "user" + i,
                        "user" + i + "@gmail.com",
                        Role.USER,
                        "google"
                ));
            }

            return results;
        }

        class SampleStudyRecruitment {
            private List<StudyRecruitment> createStudyPosts(int size) {
                List<StudyRecruitment> results = new ArrayList<>();

                for (int i = 0; i < size; i++) {
                    results.add(StudyRecruitment.savePost(
                            users.get(i),
                            "title-" + i,
                            createTechStackElements(5),
                            createCondition()
                    ));
                }

                return results;
            }

            private List<String> createTechStackElements(int size) {
                List<String> results = new ArrayList<>();

                for (int i = 0; i < size; i++) {
                    results.add("TechStack-" + i);
                }

                return results;
            }

            private Condition createCondition() {
                return Condition.createCondition(
                        "sample place",
                        LocalDateTime.of(2021, 5, 21, 0, 0),
                        LocalDateTime.of(2021, 6, 7, 0, 0),
                        5,
                        "sample explanation"
                );
            }
        }

        private List<Comment> createComments(Board post, int size) {
            List<Comment> results = new ArrayList<>();

            int startIdx = 50;

            for (int i=startIdx; i<startIdx+size; i++) {
                Comment comment = Comment.createComment(
                        post, users.get(i), "Sample Comment Content-" + i);

                createReplies(comment, 3);

                results.add(comment);
            }

            return results;
        }

        private List<Reply> createReplies(Comment comment, int size) {
            List<Reply> results = new ArrayList<>();

            int startIdx = 100;

            for (int i=startIdx; i<startIdx+size; i++) {
                results.add(
                        Reply.createReply(
                                comment, users.get(i), "Sample Comment Content-" + i));
            }

            return results;
        }
    }
}
