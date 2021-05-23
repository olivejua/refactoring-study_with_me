package com.olivejua.study;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.Reply;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.*;
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
import java.util.List;

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

        private static List<User> users;

        @Transactional
        public void init() {
            // TODO User 생성
            users = getUsers();

            // TODO StudyRecruitment 생성 (+ TechStack 생성, Comment 생성, Reply 생성)
            getStudyPosts();

            // TODO PlaceRecommendation 생성 (+ Link 생성, Like 생성, Comment 생성, Reply 생성)
            getPlacePosts();

            // TODO Question 생성 (+ Comment 생성, Reply 생성)
            getQuestionPosts();
        }

        private List<User> getUsers() {
            List<User> users = createUsers(200);
            users.forEach(em::persist);

            return users;
        }

        private List<StudyRecruitment> getStudyPosts() {
            List<StudyRecruitment> posts = SampleStudyRecruitment.createStudyPosts(100);

            for (StudyRecruitment post : posts) {
                persistStudy(post);

                List<Comment> comments = SampleComment.createComments(post, 3);

                comments.forEach(this::persistComment);
            }

            return posts;
        }

        private List<PlaceRecommendation> getPlacePosts() {
            List<PlaceRecommendation> posts = SamplePlaceRecommendation.createPlacePosts(100);

            for (PlaceRecommendation post : posts) {
                persistPlace(post);

                List<Comment> comments = SampleComment.createComments(post, 3);

                comments.forEach(this::persistComment);
            }

            return posts;
        }

        private List<Question> getQuestionPosts() {
            List<Question> posts = SampleQuestion.createPostQuestion(100);

            for (Question post : posts) {
                persistQuestion(post);

                List<Comment> comments = SampleComment.createComments(post, 3);

                comments.forEach(this::persistComment);
            }

            return posts;
        }

        private void persistStudy(StudyRecruitment post) {
            em.persist(post);
            post.getTechStack().forEach(em::persist);
        }

        private void persistPlace(PlaceRecommendation post) {
            em.persist(post);
            post.getLinks().forEach(em::persist);
            SamplePlaceRecommendation.createLikeHistory(5, post)
                    .forEach(em::persist);
        }

        private void persistQuestion(Question post) {
            em.persist(post);
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

        static class SampleQuestion {
            private static List<Question> createPostQuestion(int size) {
                List<Question> results = new ArrayList<>();

                for (int i=0; i<size; i++) {
                    results.add(Question.savePost(
                            users.get(i),
                            "title-"+i,
                            "content-"+i));
                }

                return results;
            }
        }

        static class SamplePlaceRecommendation {
            private static List<PlaceRecommendation> createPlacePosts(int size) {
                List<PlaceRecommendation> results = new ArrayList<>();

                for (int i=0; i<size; i++) {
                    results.add(PlaceRecommendation.savePost(
                            users.get(i),
                            "title-"+i,
                            "address-"+i,
                            "addressDetail-"+i,
                            "thumbnailPath-"+i,
                            "content-"+i,
                            createLinksString(5)
                    ));
                }

                return results;
            }

            private static List<String> createLinksString(int size) {
                List<String> results = new ArrayList<>();

                for (int i=0; i<size; i++) {
                    results.add("Link-" + i);
                }

                return results;
            }

            private static List<LikeHistory> createLikeHistory(int size, PlaceRecommendation post) {
                List<LikeHistory> results = new ArrayList<>();

                int half = size/2;

                for (int i=0; i<half; i++) {
                    results.add(LikeHistory.createLikeHistory(post, users.get(i), true));
                }

                for (int i=half; i<size; i++) {
                    results.add(LikeHistory.createLikeHistory(post, users.get(i), false));
                }

                return results;
            }
        }

        static class SampleStudyRecruitment {
            private static List<StudyRecruitment> createStudyPosts(int size) {
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

            private static List<String> createTechStackElements(int size) {
                List<String> results = new ArrayList<>();

                for (int i = 0; i < size; i++) {
                    results.add("TechStack-" + i);
                }

                return results;
            }

            private static Condition createCondition() {
                return Condition.createCondition(
                        "sample place",
                        LocalDateTime.of(2021, 5, 21, 0, 0),
                        LocalDateTime.of(2021, 6, 7, 0, 0),
                        5,
                        "sample explanation"
                );
            }
        }

        static class SampleComment {
            private static List<Comment> createComments(Board post, int size) {
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

            private static List<Reply> createReplies(Comment comment, int size) {
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
}
