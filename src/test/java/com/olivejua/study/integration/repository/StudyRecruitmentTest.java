package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.StudyRecruitment;
import com.olivejua.study.domain.User;
import com.olivejua.study.integration.IntegrationTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudyRecruitmentTest extends IntegrationTest {

    @Test
    void save() {
        User author = userFactory.user();

        List<String> techs = List.of("Java", "Spring Framework", "Spring Data JPA");
        StudyRecruitment post = studyRecruitmentFactory.post(author, techs);

        assertNotNull(post.getId());
        assertEquals(author.getName(), post.getNameOfAuthor());
        assertTrue(post.containsTech(techs));
    }
}
