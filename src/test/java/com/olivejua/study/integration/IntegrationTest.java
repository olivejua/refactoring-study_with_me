package com.olivejua.study.integration;

import com.olivejua.study.integration.mockFactory.PlaceRecommendationFactory;
import com.olivejua.study.integration.mockFactory.QuestionFactory;
import com.olivejua.study.integration.mockFactory.StudyRecruitmentFactory;
import com.olivejua.study.integration.mockFactory.UserFactory;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.IOException;

@Import({
        UserFactory.class,
        StudyRecruitmentFactory.class,
        PlaceRecommendationFactory.class,
        QuestionFactory.class
})
@Transactional
@SpringBootTest
public class IntegrationTest {

    @Autowired
    protected UserFactory userFactory;

    @Autowired
    protected StudyRecruitmentFactory studyRecruitmentFactory;

    @Autowired
    protected PlaceRecommendationFactory placeRecommendationFactory;

    @Autowired
    protected QuestionFactory questionFactory;

    @Autowired
    protected EntityManager entityManager;

    protected MockMultipartFile getMockImage() throws IOException {
        String fileName = "testImage";
        String extension = "jpg";
        String path = "src/test/resources/images/testImage.jpg";

        return getMockMultipartFile(fileName, extension, ContentType.IMAGE_JPEG, path);
    }

    private MockMultipartFile getMockMultipartFile(String fileName, String extension, ContentType contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName,
                fileName + "." + extension,
                contentType.toString(),
                fileInputStream);
    }
}
