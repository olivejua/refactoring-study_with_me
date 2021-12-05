package com.olivejua.study.repository;

import com.olivejua.study.domain.Link;
import com.olivejua.study.domain.PlaceRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByPost(PlaceRecommendation post);

    void deleteByPost(PlaceRecommendation post);
}
