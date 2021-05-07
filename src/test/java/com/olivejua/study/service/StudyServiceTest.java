package com.olivejua.study.service;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.domain.board.TechStack;
import com.olivejua.study.repository.CommentRepository;
import com.olivejua.study.repository.ReplyRepository;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.repository.board.TechStackRepository;
import com.olivejua.study.sampleData.SampleComment;
import com.olivejua.study.sampleData.SampleStudyRecruitment;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.board.SearchDto;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import com.olivejua.study.web.dto.board.study.PostReadResponseDto;
import com.olivejua.study.web.dto.board.study.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.study.SearchType;
import com.olivejua.study.web.dto.comment.CommentReadResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudyServiceTest {

    @Autowired
    StudyService studyService;

    @Autowired
    StudyRecruitmentRepository studyRepository;

    @Autowired
    TechStackRepository techStackRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    UserRepository userRepository;

    private User writer;

    @BeforeEach
    void setup() {
        writer = userRepository.save(SampleUser.create());
    }

    @AfterEach
    void cleanup() {
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

        Long postId = studyService.post(requestDto, writer);

        StudyRecruitment savedPost = studyRepository.findById(postId).orElse(null);

        assertNotNull(savedPost);
        assertEquals(requestDto.getTitle(), savedPost.getTitle());

        assertEquals(requestDto.getTechStack(), toStringArray(savedPost.getTechStack()));
        assertEquals(requestDto.getCondition().getPlace(), savedPost.getCondition().getPlace());
        assertEquals(requestDto.getCondition().getStartDate(), savedPost.getCondition().getStartDate());
        assertEquals(requestDto.getCondition().getEndDate(), savedPost.getCondition().getEndDate());
        assertEquals(requestDto.getCondition().getCapacity(), savedPost.getCondition().getCapacity());
        assertEquals(requestDto.getCondition().getExplanation(), savedPost.getCondition().getExplanation());
    }

    @Test
    void update() {
        Long postId = beforeUpdating();

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title("스터디원 구합니다. -수정")
                .place("강남 어딘가... -수정")
                .techStack(Arrays.asList("node.js", "typescript", "react"))
                .startDate(LocalDateTime.of(2021, 1, 1, 15, 0))
                .endDate(LocalDateTime.of(2021, 6, 30, 23, 59))
                .capacity(10)
                .explanation("소통 잘 되시는 분 구해여 -수정")
                .build();

        studyService.update(postId, requestDto);

        StudyRecruitment updatedPost = studyRepository.findById(postId).orElse(null);

        assertNotNull(updatedPost);
        assertEquals(requestDto.getTechStack(), toStringArray(updatedPost.getTechStack()));
        assertEquals(requestDto.getCondition().getPlace(), updatedPost.getCondition().getPlace());
        assertEquals(requestDto.getCondition().getStartDate(), updatedPost.getCondition().getStartDate());
        assertEquals(requestDto.getCondition().getEndDate(), updatedPost.getCondition().getEndDate());
        assertEquals(requestDto.getCondition().getCapacity(), updatedPost.getCondition().getCapacity());
        assertEquals(requestDto.getCondition().getExplanation(), updatedPost.getCondition().getExplanation());
    }

    @Test
    void delete() {
        Long postId = beforeUpdating();

        studyService.delete(postId);

        StudyRecruitment deletedPost = studyRepository.findById(postId).orElse(null);
        assertNull(deletedPost);
    }

    @Test
    void read() {
        //given
        Long postId = beforeUpdating();
        StudyRecruitment post = studyRepository.findById(postId).orElse(null);

        List<Comment> comments = SampleComment.createList(writer, post, 5);
        comments.forEach(commentRepository::save);

        //when
        PostReadResponseDto responseDto = studyService.read(postId);

        assertEquals(post.getId(), responseDto.getPostId());
        assertEquals(post.getTitle(), responseDto.getTitle());
        assertEquals(toStringArray(post.getTechStack()), responseDto.getTechStack());
//        assertEquals(post.getComment().size(), responseDto.getComments().size());
//        for (CommentReadResponseDto comment : responseDto.getComments()) {
//            System.out.println("comment.getContent() = " + comment.getContent());
//        }
    }

    @Test
    void list() {
        createSampleData();

        PageRequest paging = PageRequest.of(0, 20, Sort.Direction.ASC, "POST_ID");
        Page<PostListResponseDto> list = studyService.list(paging);
        for (PostListResponseDto responseDto : list) {
            System.out.println("responseDto = " + responseDto);
        }

        assertEquals(20, list.getSize());
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

    @Test
    void search() {
        createSampleData();

        PageRequest paging = PageRequest.of(0, 20, Sort.Direction.ASC, "POST_ID");
        Page<PostListResponseDto> posts =
                studyService.search(new SearchDto(SearchType.TITLE.name(), "java 스터디 하실분"), paging);
        for (PostListResponseDto responseDto : posts) {
            System.out.println("responseDto = " + responseDto);
        }

        assertEquals(20, posts.getSize());
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

        return studyService.post(requestDto, writer);
    }

    private List<String> toStringArray(List<TechStack> techStack) {
        return techStack.stream()
                .map(TechStack::getElement)
                .collect(Collectors.toList());
    }
}