package com.olivejua.study.repository.board;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Language;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.sampleData.SampleLanguage;
import com.olivejua.study.sampleData.SampleStudyRecruitment;
import com.olivejua.study.sampleData.SampleUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudyRecruitmentRepositoryTest {

    @Autowired
    StudyRecruitmentRepository studyRecruitmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Test
    @DisplayName("StudyRecruitment - 저장")
    public void save() {
        // given
        User writer = SampleUser.create();
        userRepository.save(writer);

        List<Language> languages = SampleLanguage.createList();
        languages.forEach(language -> languageRepository.save(language));

        StudyRecruitment post = SampleStudyRecruitment.create(writer, languages);

        // when
        studyRecruitmentRepository.save(post);

        //then
        StudyRecruitment findPost = studyRecruitmentRepository.findAll().get(0);

        assertEquals(post, findPost);
        assertEquals(post.getId(), findPost.getId());
        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getWriter(), findPost.getWriter());
        assertEquals(post.getCondition(), findPost.getCondition());
    }
}