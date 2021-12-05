package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.Link;
import com.olivejua.study.domain.PlaceRecommendation;
import com.olivejua.study.domain.User;
import com.olivejua.study.integration.IntegrationTest;
import com.olivejua.study.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceRecommendationTest extends IntegrationTest {
    @Autowired
    private LinkRepository linkRepository;

    @Test
    void save() {
        //Given
        User author = userFactory.user();

        //When
        PlaceRecommendation post = placeRecommendationFactory.post(author);

        //Then
        assertNotNull(post.getId());
        assertEquals(author.getName(), post.getNameOfAuthor());

        List<Link> savedLinksOfPost = linkRepository.findByPost(post);
        assertEquals(post.getSizeOfLinks(), savedLinksOfPost.size());
        assertTrue(post.getLinks().containsAll(savedLinksOfPost));
    }
}
