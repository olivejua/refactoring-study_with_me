package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.domain.board.TechStack;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.repository.board.TechStackRepository;
import com.olivejua.study.sampleData.SampleStudyRecruitment;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import com.olivejua.study.web.dto.board.study.PostReadResponseDto;
import com.olivejua.study.web.dto.board.study.PostSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudyServiceTest extends CommonBoardServiceTest {

    @Autowired
    StudyService studyService;

    @Autowired
    StudyRecruitmentRepository studyRepository;

    @Autowired
    TechStackRepository techStackRepository;

    @AfterEach
    void cleanup() {
        replyRepository.deleteAll();
        commentRepository.deleteAll();
        techStackRepository.deleteAll();
        studyRepository.deleteAll();
        userRepository.deleteAll();
    }

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

        Long postId = studyService.post(requestDto, dummyPostWriter);

        StudyRecruitment savedPost = studyRepository.findById(postId).orElse(null);

        assertNotNull(savedPost);
        assertEquals(requestDto.getTitle(), savedPost.getTitle());
        assertEquals(requestDto.getTechStack(), toStringArray(savedPost.getTechStack()));
        assertEquals(requestDto.getCapacity(), savedPost.getCondition());
    }

    @Test
    void update() {
        //when
        Condition updatedCondition = Condition.createCondition(
                "sample place2",
                LocalDateTime.of(2021, 07, 01, 00, 00),
                LocalDateTime.of(2022, 01, 31, 00, 00),
                20,
                "sample explanation blah blah updated");
        String updatedTitle = "sample title2";

        List<String> updatedTechStack = new ArrayList<>();
        updatedTechStack.add("sample tech5");
        updatedTechStack.add("sample tech6");
        updatedTechStack.add("sample tech7");
        updatedTechStack.add("sample tech8");

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(updatedTitle)
                .place(updatedCondition.getPlace())
                .techStack(updatedTechStack)
                .startDate(updatedCondition.getStartDate())
                .endDate(updatedCondition.getEndDate())
                .capacity(updatedCondition.getCapacity())
                .explanation(updatedCondition.getExplanation())
                .build();

        studyService.update(dummyPost.getId(), requestDto);

        //then
        StudyRecruitment updatedPost = studyRepository.findById(dummyPost.getId()).orElse(null);

        assertNotNull(updatedPost);
        assertEquals(requestDto.getCondition(), updatedPost.getCondition());
        assertEquals(updatedTechStack, toStringArray(updatedPost.getTechStack()));
    }

    @Test
    void delete() {
        studyService.delete(dummyPost.getId());

        Optional<StudyRecruitment> deletedEntity = studyRepository.findById(dummyPost.getId());
        assertFalse(deletedEntity.isPresent());
    }

    @Test
    void read() {
        //when
        PostReadResponseDto responseDto = studyService.read(dummyPost.getId(), dummyServletPath);

        assertThat(dummyPost).isExactlyInstanceOf(StudyRecruitment.class);
        StudyRecruitment dummyPostInStudy = (StudyRecruitment) dummyPost;

        assertEquals(dummyPostInStudy.getId(), responseDto.getPostId());
        assertEquals(dummyPostInStudy.getTitle(), responseDto.getTitle());
        assertEquals(toStringArray(dummyPostInStudy.getTechStack()), responseDto.getTechStack());
        assertEquals(dummyPostInStudy.getComment().size(), responseDto.getComments().size());
    }

    @Test
    void list() {
        createSampleData();

        PageRequest paging = PageRequest.of(1, 20, Sort.Direction.ASC, "POST_ID");
        Page<PostListResponseDto> list = studyService.list(paging);

        assertEquals(20, list.getSize());
    }

    @Test
    void search() {
        createSampleData();

        PageRequest paging = PageRequest.of(1, 20, Sort.Direction.ASC, "POST_ID");
        Page<PostListResponseDto> posts =
                studyService.search(new SearchDto(SearchType.TITLE.name(), "java 스터디 하실분"), paging);

        assertEquals(20, posts.getSize());
    }

    private void createSampleData() {
        List<String[]> tsList = new ArrayList<>();
        tsList.add(new String[] {"java", "spring", "aws"});
        tsList.add(new String[] {"java", "spring", "aws", "jpa"});
        tsList.add(new String[] {"java", "jsp", "servlet", "mybatis"});
        tsList.add(new String[] {"python", "gcp", "react"});
        tsList.add(new String[] {"python", "aws", "html"});

        List<String> titles = new ArrayList<>();
        titles.add("java 모집합니다.");
        titles.add("java 스터디 하실분");
        titles.add("spring 프로젝트하실분");
        titles.add("python 알고리즘 같이 하실분");
        titles.add("aws 공부하실분 오세요");

        List<User> users = SampleUser.createList(5);

        List<StudyRecruitment> posts = SampleStudyRecruitment.createList100(users, titles, tsList);
        posts.forEach(post -> {
            userRepository.save(post.getWriter());
            studyRepository.save(post);
            post.getTechStack()
                    .forEach(techStackRepository::save);
        });
    }

    private Long beforeUpdating() {
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title("스터디원 구합니다.")
                .place("강남 어딘가...")
                .techStack(Arrays.asList("java", "spring"))
                .startDate(LocalDateTime.of(2021, 5, 1, 15, 0))
                .endDate(LocalDateTime.of(2021, 7, 31, 23, 59))
                .capacity(5)
                .explanation("소통 잘 되시는 분 구해여")
                .build();

        return studyService.post(requestDto, dummyPostWriter);
    }

    private List<String> toStringArray(List<TechStack> techStack) {
        return techStack.stream()
                .map(TechStack::getElement)
                .collect(Collectors.toList());
    }

    @Override
    void saveDummyPost(Board post) {
        if (post instanceof StudyRecruitment) {
            StudyRecruitment postInStudy = (StudyRecruitment) post;
            studyRepository.save(postInStudy);
            postInStudy.getTechStack().forEach(techStackRepository::save);

            dummyPost = postInStudy;
        }
    }

    @Override
    Board createDummyPost() {
        List<String> techStack = new ArrayList<>();
        techStack.add("sample tech1");
        techStack.add("sample tech2");
        techStack.add("sample tech3");

        Condition condition = Condition.createCondition(
                "sample place",
                LocalDateTime.of(2020, 06, 12, 00, 00),
                LocalDateTime.of(2020, 12, 12, 00, 00),
                10,
                "sample explanation"
        );

        return StudyRecruitment.createPost(
                dummyPostWriter,
                "sample title",
                techStack,
                condition
        );
    }
}