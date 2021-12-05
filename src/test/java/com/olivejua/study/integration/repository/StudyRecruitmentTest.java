package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.StudyRecruitment;
import com.olivejua.study.domain.Tech;
import com.olivejua.study.domain.User;
import com.olivejua.study.integration.IntegrationTest;
import com.olivejua.study.repository.TechRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudyRecruitmentTest extends IntegrationTest {
    @Autowired
    private TechRepository techRepository;

    @Test
    void save() {
        //Given
        User author = userFactory.user();
        List<String> techs = List.of("Java", "Spring Framework", "Spring Data JPA");

        //When
        StudyRecruitment post = studyRecruitmentFactory.post(author, techs);

        //Then
        assertNotNull(post.getId());
        assertEquals(author.getName(), post.getNameOfAuthor());
        assertTrue(post.containsTech(techs));

        List<Tech> savedTechsOfPost = techRepository.findByPost(post);
        assertEquals(post.getSizeOfTechs(), savedTechsOfPost.size());
    }
}
