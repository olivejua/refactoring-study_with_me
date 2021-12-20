package com.olivejua.study.repository.query;

import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceRecommendationQueryRepository {
    Page<PlaceRecommendation> findPosts(Pageable pageable);
}
