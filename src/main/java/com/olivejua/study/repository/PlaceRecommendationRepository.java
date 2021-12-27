package com.olivejua.study.repository;

import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import com.olivejua.study.repository.query.PlaceRecommendationQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRecommendationRepository extends JpaRepository<PlaceRecommendation, Long>, PlaceRecommendationQueryRepository {
}
