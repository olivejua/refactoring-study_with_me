package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionQueryRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionRepositoryTest extends CommonBoardRepositoryTest {

    /**
     * Dependency Injection
     */
    @Autowired
    private QuestionQueryRepository questionQueryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * test template zone
     */
    @Override
    void setup() {}

    @Override
    void clearAll() {
        questionRepository.deleteAll();
    }


    /**
     * test zone
     */
    @Test
    @DisplayName("post id로 하나의 entity를 조회한다")
    void testFindEntity() {
        //given
        Question post = (Question) dummyPosts.get(5);

        //when
        Question findPost = questionQueryRepository.findEntity(post.getId()).orElse(null);

        //then
        assertNotNull(findPost);
        assertEquals(post.getId(), findPost.getId());
        assertEquals(post.getContent(), findPost.getContent());
        assertNotNull(findPost.getWriter());
        assertNotNull(findPost.getComment());
    }

    @Test
    @DisplayName("모든 entity 목록을 조회한다")
    void testFindEntities() {
        //given
        PageRequest paging = PageRequest.of(0, 10);

        //when
        Page<PostListResponseDto> findEntities = questionQueryRepository.findEntities(paging);

        //then
        assertEquals(dummyPosts.size(), findEntities.getTotalElements());
        assertEquals(10, findEntities.getContent().size());

        List<PostListResponseDto> content = findEntities.getContent();
        PostListResponseDto post = content.get(0);
        assertNotNull(post.getWriterName());
        assertEquals(1, post.getCommentCount());
    }

    @Test
    @DisplayName("검색된 entity 목록을 조회한다")
    void testFindEntitiesWith() {
        //given
        Question post = questionRepository.save(
                Question.savePost(dummyWriter, "search target title", "search target content"));
        dummyPosts.add(post);

        PageRequest paging = PageRequest.of(0, 10);
        SearchDto searchCond = new SearchDto(SearchType.TITLE.name(), "target title");

        //when
        Page<PostListResponseDto> findEntities = questionQueryRepository.findEntitiesWith(searchCond, paging);

        //then
        assertEquals(1, findEntities.getTotalElements());

        Question expectedPost = (Question) dummyPosts.get(dummyPosts.size() - 1);
        PostListResponseDto actualPost = findEntities.getContent().get(0);

        assertEquals(expectedPost.getTitle(), actualPost.getTitle());
        assertEquals(expectedPost.getWriter().getName(), actualPost.getWriterName());
        assertEquals(expectedPost.getComment().size(), actualPost.getCommentCount());
    }

    /**
     * overriding zone
     */
    @Override
    Board createDummyPost() {
        Question post = Question.savePost(dummyWriter, "sample title", "sample content");
        return questionRepository.save(post);
    }
}
