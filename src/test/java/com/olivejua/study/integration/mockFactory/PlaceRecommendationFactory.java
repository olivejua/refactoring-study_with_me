package com.olivejua.study.integration.mockFactory;

import com.olivejua.study.common.mockData.MockPlaceRecommendation;
import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.repository.PlaceRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

@TestComponent
public class PlaceRecommendationFactory {
    @Autowired
    private PlaceRecommendationRepository placeRecommendationRepository;

    private static final String MOCK_TITLE = "This place is goooood! X)";
    private static final String MOCK_ADDRESS = "Seoulsi Gannamgu";
    private static final String MOCK_ADDRESS_DETAIL = "momo building third floor";
    private static final List<String> MOCK_LINKS =
            List.of("www.naver.com/place/blog/12345",
                    "www.google.com/place/cafe/12345");

    /****************************************************************************
     * Create a Post
     ***************************************************************************/
    public PlaceRecommendation post(User author) {
        PlaceRecommendation post = MockPlaceRecommendation.builder()
                .author(author)
                .title(MOCK_TITLE)
                .address(MOCK_ADDRESS)
                .addressDetail(MOCK_ADDRESS_DETAIL)
                .links(MOCK_LINKS)
                .build();

        return placeRecommendationRepository.save(post);
    }

    public PlaceRecommendation post(User author, List<String> links) {
        PlaceRecommendation post = MockPlaceRecommendation.builder()
                .author(author)
                .title(MOCK_TITLE)
                .address(MOCK_ADDRESS)
                .addressDetail(MOCK_ADDRESS_DETAIL)
                .links(links)
                .build();

        return placeRecommendationRepository.save(post);
    }
}
