package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.domain.board.TechStack;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.sampleData.SampleStudyRecruitment;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.study.PostSaveRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class StudyServiceTest {

    @Autowired
    StudyService studyService;

    @Autowired
    StudyRecruitmentRepository studyRepository;

    @Autowired
    UserRepository userRepository;

    private User writer;

    @BeforeEach
    void setup() {
        writer = userRepository.save(SampleUser.create());
    }

//    @AfterEach
//    void cleanup() {
//        studyRepository.deleteAll();
//        userRepository.deleteAll();
//    }

    @Test
    void post() {
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title("스터디원 구합니다.")
                .place("강남 어딘가...")
                .techStack(Arrays.asList("java", "spring"))
                .startDate(LocalDateTime.of(2021, 5, 1, 15, 0))
                .endDate(LocalDateTime.of(2021, 7, 31, 23, 59))
                .capacity(5)
                .explanation("소통 잘 되시는 분 구해여")
                .build();

        Long postId = studyService.post(requestDto, writer);

        StudyRecruitment post = studyRepository.findById(postId).orElse(null);

        assertNotNull(post);
//        assertEquals(requestDto.getTitle(), savedPost.getTitle());
//        assertEquals(requestDto.getCondition().getLanguages(), savedPost.getCondition().getLanguages());
//        assertEquals(requestDto.getCondition().getPlace(), savedPost.getCondition().getPlace());
//        assertEquals(requestDto.getCondition().getStartDate(), savedPost.getCondition().getStartDate());
//        assertEquals(requestDto.getCondition().getEndDate(), savedPost.getCondition().getEndDate());
//        assertEquals(requestDto.getCondition().getCapacity(), savedPost.getCondition().getCapacity());
//        assertEquals(requestDto.getCondition().getExplanation(), savedPost.getCondition().getExplanation());
    }
}