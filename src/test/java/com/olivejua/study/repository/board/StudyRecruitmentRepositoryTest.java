package com.olivejua.study.repository.board;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.TechStack;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.sampleData.SampleStudyRecruitment;
import com.olivejua.study.sampleData.SampleUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudyRecruitmentRepositoryTest {

    @Autowired
    StudyRecruitmentRepository studyRecruitmentRepository;

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
    void search() {
        // given
        User writer = SampleUser.create();
        userRepository.save(writer);

        StudyRecruitment post = SampleStudyRecruitment.create(writer);

        // when
        studyRecruitmentRepository.save(post);
        post.getTechStack().forEach(techStackRepository::save);

        em.flush();
        em.clear();

        List<StudyRecruitment> search = studyRecruitmentRepository.search();
        StudyRecruitment savedPost = search.get(0);

        assertEquals(post.getId(), savedPost.getId());
        System.out.println("=============");
        assertEquals(post.getTechStack().size(), savedPost.getTechStack().size());
        assertEquals(post.getTechStack().get(0), savedPost.getTechStack().get(0));
        System.out.println("savedPost.getTechStack().get(0).getElement() = " + savedPost.getTechStack().get(0).getElement());
    }
}