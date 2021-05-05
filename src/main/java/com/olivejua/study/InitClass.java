package com.olivejua.study;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.repository.board.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        private final StudyRecruitmentRepository studyRecruitmentRepository;
        private final UserRepository userRepository;
        private final TechStackRepository techStackRepository;

        @Transactional
        public void init() {
            List<String[]> tsList = new ArrayList<>();
            tsList.add(new String[] {"java", "spring", "aws"});
            tsList.add(new String[] {"java", "spring", "aws", "jpa"});
            tsList.add(new String[] {"java", "jsp", "servlet", "mybatis"});
            tsList.add(new String[] {"python", "gcp", "react"});
            tsList.add(new String[] {"python", "aws", "html"});

            List<String> titles = new ArrayList<>();
            titles.add("java");
            titles.add("java");
            titles.add("spring");
            titles.add("python");
            titles.add("aws");

            List<User> users = createUserList(5);

            List<StudyRecruitment> posts = createPosts(users, titles, tsList);
            posts.forEach(post -> {
                userRepository.save(post.getWriter());
                studyRecruitmentRepository.save(post);
                post.getTechStack()
                        .forEach(techStackRepository::save);
            });
        }

        public static List<User> createUserList(int size) {
            List<User> result = new ArrayList<>();

            for(int i=1; i<=size; i++) {
                result.add(
                        User.builder()
                                .name("user"+i)
                                .email("user" + i + "@gmail.com")
                                .role(Role.GUEST)
                                .socialCode("google")
                                .build()
                );
            }

            return result;
        }

        static List<StudyRecruitment> createPosts(List<User> writer, List<String> titles, List<String[]> techStacks) {
            List<StudyRecruitment> results = new ArrayList<>();

            for(int i=0; i<writer.size(); i++) {
                results.add(create(writer.get(i), titles.get(i), techStacks.get(i)));
            }

            return results;
        }

        static StudyRecruitment create(User writer, String title, String[] techStack) {
            return StudyRecruitment.savePost(writer, title, createTechStack(techStack), SampleCondition.create());
        }

        static class SampleCondition {
            static Condition create() {
                return Condition.builder()
                        .place("강남")
                        .startDate(LocalDateTime.of(2021, 4, 7, 0, 0))
                        .endDate(LocalDateTime.of(2021, 6, 7, 0, 0))
                        .capacity(5)
                        .explanation("java 프로젝트 할 사람 모집")
                        .build();
            }
        }

        static List<String> createTechStack() {
            List<String> techStack = new ArrayList<>();
            techStack.add("java");
            techStack.add("spring");
            techStack.add("jpa");
            techStack.add("gcp");
            techStack.add("mysql");
            return techStack;
        }

        static List<String> createTechStack(String[] techStack) {
            return Arrays.stream(techStack)
                    .collect(Collectors.toList());
        }
    }


}
