package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.unit.repository.UserRepository;
import com.olivejua.study.unit.repository.board.PlaceRecommendationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LinkTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaceRecommendationRepository placeRepository;

    @Test
    @DisplayName("링크목록이 모두 같아야 true")
    public void equalsTest_success() throws Exception {
        List<Link> links1 = createLinks(new String[] {"www.google.com", "www.naver.com", "www.tistory.com", "www.daum.net", "www.github.com"});
        List<Link> links2 = createLinks(new String[] {"www.google.com", "www.naver.com", "www.tistory.com", "www.daum.net", "www.github.com"});

        assertThat(links1.equals(links2)).isTrue();
    }

    @Test
    @DisplayName("링크목록이 하나라도 다르면 false")
    public void equalsTest_fail() throws Exception {
        List<Link> links1 = createLinks(new String[] {"www.google.com", "www.naver.com", "www.tistory.com", "www.daum.net", "www.github.com"});
        List<Link> links2 = createLinks(new String[] {"www.google.com", "www.naver.com", "www.tistory.com", "www.youtube.com", "www.github.com"});

        assertThat(links1.equals(links2)).isFalse();
    }

    public static List<Link> createLinks(String[] links) {
        User writer = User.createUser(
                "김슬기",
                "tmfrl4710@gmail.com",
                Role.GUEST,
                "google"
        );

        PlaceRecommendation post = PlaceRecommendation.savePost(writer, "제목", "주소",
                "상세주소", "/tmp", "내용", Arrays.asList(links));

        return post.getLinks();
    }
}