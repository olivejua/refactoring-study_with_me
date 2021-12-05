package com.olivejua.study.repository;

import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRecommendationRepository extends JpaRepository<PlaceRecommendation, Long> {
}
