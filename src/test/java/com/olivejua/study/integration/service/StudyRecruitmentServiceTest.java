package com.olivejua.study.integration.service;

import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.domain.studyRecruitment.Condition;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.studyRecruitment.Tech;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.integration.IntegrationTest;
import com.olivejua.study.repository.StudyRecruitmentRepository;
import com.olivejua.study.repository.TechRepository;
import com.olivejua.study.service.UploadService;
import com.olivejua.study.service.studyRecruitment.StudyRecruitmentService;
import com.olivejua.study.utils.PostImagePaths;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentSaveRequestDto;
import com.olivejua.study.web.dto.studyRecruitment.StudyRecruitmentUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class StudyRecruitmentServiceTest extends IntegrationTest {

    @Autowired
    private StudyRecruitmentService studyRecruitmentService;

    @Autowired
    private StudyRecruitmentRepository studyRecruitmentRepository;

    @Autowired
    private TechRepository techRepository;

    @Autowired
    private EntityManager entityManager;

    @MockBean
    private UploadService uploadService;

    private User author;
    private LoginUser loginUser;
    private final String MOCK_DOMAIN_URL = "https://aws.s3.simplelog.com/";
    private final String POST_IMAGE_PATH = PostImagePaths.STUDY_RECRUITMENT + "1/";

    @BeforeEach
    void setup() {
        author = userFactory.user();
        loginUser = new LoginUser(author);
    }

    @Test
    @DisplayName("DB에 게시글을 저장한다")
    void testSavePost() {
        //given
        StudyRecruitmentSaveRequestDto requestDto = studyRecruitmentFactory.saveRequestDto();

        //when
        Long savedPostId = studyRecruitmentService.savePost(requestDto, loginUser.getUser());

        //post
        Optional<StudyRecruitment> optionalFindPost = studyRecruitmentRepository.findById(savedPostId);
        assertTrue(optionalFindPost.isPresent());

        StudyRecruitment findPost = optionalFindPost.get();
        assertNotNull(findPost.getCondition());

        //condition
        Condition findPostCondition = findPost.getCondition();
        assertEquals(requestDto.getMeetingPlace(), findPostCondition.getMeetingPlace());
        assertEquals(requestDto.getStartDate(), findPostCondition.getStartDate());
        assertEquals(requestDto.getEndDate(), findPostCondition.getEndDate());
        assertEquals(requestDto.getCapacity(), findPostCondition.getCapacity());
        assertEquals(requestDto.getExplanation(), findPostCondition.getExplanation());
    }

    @Test
    @DisplayName("DB에 게시글을 저장하고, 기술스택도 같이 저장한다")
    void testSavePost_withTechs() throws IOException {
        //given
        StudyRecruitmentSaveRequestDto requestDto = studyRecruitmentFactory.saveRequestDto();

        //when
        Long savedPostId = studyRecruitmentService.savePost(requestDto, loginUser.getUser());

        entityManager.flush();
        entityManager.clear();

        //then
        Optional<StudyRecruitment> optionalFindPost = studyRecruitmentRepository.findById(savedPostId);
        assertTrue(optionalFindPost.isPresent());
        StudyRecruitment findPost = optionalFindPost.get();

        //techs
        assertTrue(requestDto.getTechs().containsAll(
                findPost.getElementsOfTechs()
        ));
        List<Tech> findTechs = techRepository.findByPost(findPost);
        assertEquals(requestDto.getTechs().size(), findTechs.size());
    }

    @Test
    @DisplayName("DB에 게시글을 저장하고, 이미지도 같이 저장한다")
    void testSavePost_withImages() throws IOException {
        //given
        StudyRecruitmentSaveRequestDto requestDto =
                studyRecruitmentFactory.saveRequestDto(List.of(getMockImage(), getMockImage()));

        List<String> mockImagePaths = List.of(POST_IMAGE_PATH + "1.jpg", POST_IMAGE_PATH + "2.jpg");
        List<String> mockDomainImagePaths = mockImagePaths.stream()
                .map(imagePath -> MOCK_DOMAIN_URL + imagePath)
                .collect(Collectors.toList());

        when(uploadService.upload(anyList(), anyString())).thenReturn(mockDomainImagePaths);

        //when
        Long savedPostId = studyRecruitmentService.savePost(requestDto, loginUser.getUser());

        entityManager.flush();
        entityManager.clear();

        //then
        assertNotNull(savedPostId);

        //post
        Optional<StudyRecruitment> optionalFindPost = studyRecruitmentRepository.findById(savedPostId);
        assertTrue(optionalFindPost.isPresent());
        StudyRecruitment findPost = optionalFindPost.get();

        //images
        assertTrue(findPost.hasImages());
        assertEquals(mockImagePaths, findPost.getImagePaths());
    }


    @Test
    @DisplayName("DB에 게시글을 수정한다")
    void testUpdatePost() {
        //given
        StudyRecruitment post = studyRecruitmentFactory.post(author);

        StudyRecruitmentUpdateRequestDto requestDto = studyRecruitmentFactory.updateRequestDto();

        //when
        studyRecruitmentService.updatePost(post.getId(), requestDto, author);

        //then
        entityManager.flush();
        entityManager.clear();

        Optional<StudyRecruitment> optionalFindPost = studyRecruitmentRepository.findById(post.getId());
        assertTrue(optionalFindPost.isPresent());
        StudyRecruitment findPost = optionalFindPost.get();

        //condition
        assertNotNull(findPost.getCondition());
        Condition findPostCondition = findPost.getCondition();
        assertEquals(requestDto.getMeetingPlace(), findPostCondition.getMeetingPlace());
        assertEquals(requestDto.getStartDate(), findPostCondition.getStartDate());
        assertEquals(requestDto.getEndDate(), findPostCondition.getEndDate());
        assertEquals(requestDto.getCapacity(), findPostCondition.getCapacity());
        assertEquals(requestDto.getExplanation(), findPostCondition.getExplanation());
    }

    @Test
    @DisplayName("DB에 게시글을 수정하고, 기술스택도 같이 저장한다")
    void testUpdatePost_withTechs() throws IOException {
        //given
        StudyRecruitment post = studyRecruitmentFactory.post(author);
        StudyRecruitmentUpdateRequestDto requestDto = studyRecruitmentFactory.updateRequestDto();

        //when
        studyRecruitmentService.updatePost(post.getId(), requestDto, author);

        //then
        entityManager.flush();
        entityManager.clear();

        Optional<StudyRecruitment> optionalFindPost = studyRecruitmentRepository.findById(post.getId());
        assertTrue(optionalFindPost.isPresent());
        StudyRecruitment findPost = optionalFindPost.get();

        //techs
        assertTrue(requestDto.getTechs().containsAll(
                findPost.getElementsOfTechs()
        ));
        List<Tech> findTechs = techRepository.findByPost(findPost);
        assertEquals(requestDto.getTechs().size(), findTechs.size());
    }

    @Test
    @DisplayName("DB에 게시글을 저장하고, 이미지도 같이 저장한다")
    void testUpdatePost_withImages() throws IOException {
        //given
        List<String> mockSavedImagePaths = List.of(POST_IMAGE_PATH + "1.jpg", POST_IMAGE_PATH + "2.jpg");
        StudyRecruitment post = studyRecruitmentFactory.postWithImages(author, mockSavedImagePaths);
        StudyRecruitmentUpdateRequestDto requestDto = studyRecruitmentFactory.updateRequestDto();

        List<String> mockUpdatedImagePaths = List.of(POST_IMAGE_PATH + "3.jpg", POST_IMAGE_PATH + "4.jpg", POST_IMAGE_PATH + "5.jpg");
        List<String> mockDomainImagePaths = mockUpdatedImagePaths.stream()
                .map(imagePath -> MOCK_DOMAIN_URL + imagePath)
                .collect(Collectors.toList());

        when(uploadService.upload(anyList(), anyString())).thenReturn(mockDomainImagePaths);

        //when
        studyRecruitmentService.updatePost(post.getId(), requestDto, author);


    }
}
