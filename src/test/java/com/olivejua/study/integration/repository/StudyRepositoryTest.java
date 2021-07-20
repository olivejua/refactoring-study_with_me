package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.Condition;
import com.olivejua.study.domain.board.StudyRecruitment;
import com.olivejua.study.repository.board.StudyRecruitmentQueryRepository;
import com.olivejua.study.repository.board.StudyRecruitmentRepository;
import com.olivejua.study.repository.board.TechStackRepository;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudyRepositoryTest extends CommonBoardRepositoryTest {

    /**
     * Dependency Injection
     */
    @Autowired
    private StudyRecruitmentRepository studyRepository;

    @Autowired
    private StudyRecruitmentQueryRepository studyQueryRepository;

    @Autowired
    private TechStackRepository techStackRepository;


    /**
     * test template zone
     */
    @Override
    void setup() {}

    @Override
    void clearAll() {
        techStackRepository.deleteAll();
        studyRepository.deleteAll();
    }

    /**
     * test zone
     */
    @Test
    @DisplayName("하나의 entity를 조회한다")
    void testFindEntity() {
        //given
        StudyRecruitment post = (StudyRecruitment) dummyPosts.get(1);
        em.flush();
        em.clear();

        //when
        StudyRecruitment findPost = studyQueryRepository.findEntity(post.getId()).orElse(null);

        //given
        assertNotNull(findPost);
        assertEquals(post.getId(), findPost.getId());
        assertEquals(post.getCondition().getExplanation(), findPost.getCondition().getExplanation());
        assertEquals(post.getCondition().getEndDate(), findPost.getCondition().getEndDate());
        assertTrue(post.getTechStack().containsAll(findPost.getTechStack()));
        assertEquals(post.getComment().size(), findPost.getComment().size());
    }

    @Test
    @DisplayName("여러개의 entities를 조회한다")
    void testFindEntities() {
        //given
        PageRequest paging = PageRequest.of(0, 10);

        //when
        Page<PostListResponseDto> entities = studyQueryRepository.findEntities(paging);

        //then
        assertEquals(dummyPosts.size(), entities.getTotalElements());
        assertEquals(10, entities.getContent().size());

        StudyRecruitment expectedPost = (StudyRecruitment) dummyPosts.get(0);
        PostListResponseDto findPost = entities.getContent().get(0);
        assertEquals(expectedPost.getId(), findPost.getPostId());
        assertEquals(expectedPost.getWriter().getName(), findPost.getWriterName());
        assertEquals(expectedPost.getComment().size(), findPost.getCommentCount());
    }

    @Test
    @DisplayName("검색 조건에 맞는 여러개의 entities를 조회한다")
    void testFindEntitiesWith() {
        //given
        StudyRecruitment expectedPost = StudyRecruitment.createPost(dummyWriter,
                "search target title",
                List.of("spring", "jpa", "java"),
                Condition.createCondition(
                        "search target place",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        5,
                        "search target explanation"));

        dummyPosts.add(studyRepository.save(expectedPost));

        em.flush();
        em.clear();

        PageRequest paging = PageRequest.of(0, 10);
        SearchDto searchDto = new SearchDto(SearchType.EXPLANATION.name(), "target");

        //when
        Page<PostListResponseDto> entities = studyQueryRepository.findEntitiesWith(searchDto, paging);

        //then
        assertEquals(1, entities.getTotalElements());

        PostListResponseDto findPost = entities.getContent().get(0);
        assertEquals(expectedPost.getId(), findPost.getPostId());
        assertEquals(expectedPost.getWriter().getName(), findPost.getWriterName());
        assertEquals(expectedPost.getComment().size(), findPost.getCommentCount());
    }

    /**
     * overriding zone
     */
    @Override
    Board createDummyPost() {
        StudyRecruitment post = StudyRecruitment.createPost(
                dummyWriter,
                "sample title",
                List.of("java", "spring", "jpa"),
                Condition.createCondition(
                        "sample place",
                        LocalDateTime.of(2021, 07, 19, 00, 00),
                        LocalDateTime.of(2021, 12, 19, 00, 00),
                        10,
                        "sample explanation"));

        post.getTechStack().forEach(techStackRepository::save);
        return studyRepository.save(post);
    }
}
