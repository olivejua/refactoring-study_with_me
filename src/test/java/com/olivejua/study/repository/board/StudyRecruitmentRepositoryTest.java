package com.olivejua.study.repository.board;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.sampleData.SampleStudyRecruitment;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class StudyRecruitmentRepositoryTest {

    @Autowired
    StudyRecruitmentRepository studyRecruitmentRepository;

    @Autowired
    StudyRecruitmentQueryRepository studyQueryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TechStackRepository techStackRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("StudyRecruitment - 저장")
    void save() {
        // given
        User writer = SampleUser.create();
        userRepository.save(writer);

        StudyRecruitment post = SampleStudyRecruitment.create(writer);

        // when
        studyRecruitmentRepository.save(post);
        post.getTechStack().forEach(techStackRepository::save);

        //then
        StudyRecruitment findPost = studyRecruitmentRepository.findAll().get(0);

        assertEquals(post, findPost);
        assertEquals(post.getId(), findPost.getId());
        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getWriter(), findPost.getWriter());
        assertEquals(post.getCondition(), findPost.getCondition());
    }

    @Test
    void list() {
        // given
        User writer = SampleUser.create();
        userRepository.save(writer);

        StudyRecruitment post = SampleStudyRecruitment.create(writer);

        // when
        studyRecruitmentRepository.save(post);
        post.getTechStack().forEach(techStackRepository::save);

        em.flush();
        em.clear();

        PageRequest paging = PageRequest.of(0, 10, Sort.Direction.ASC, "POST_ID");

        Page<PostListResponseDto> posts = studyQueryRepository.list(paging);
//        PostListResponseDto listResponseDto = posts.get(0);
//
//        assertEquals(post.getId(), listResponseDto.getPostId());
    }

    @Test
    void search() {
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

        List<User> users = SampleUser.createList(5);

        List<StudyRecruitment> posts = SampleStudyRecruitment.createList100(users, titles, tsList);
        posts.forEach(post -> {
            userRepository.save(post.getWriter());
            studyRecruitmentRepository.save(post);
            post.getTechStack()
                    .forEach(ts -> techStackRepository.save(ts));
        });

        em.flush();
        em.clear();

        PageRequest paging = PageRequest.of(0, 10, Sort.Direction.ASC, "POST_ID");

        Page<PostListResponseDto> savedPosts =
                studyQueryRepository.search(new SearchDto(SearchType.TECH_STACK.name(), "java"), paging);
    }

}