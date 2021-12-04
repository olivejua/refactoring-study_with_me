package com.olivejua.study.repository.board;

import com.olivejua.study.domain.Link;
import com.olivejua.study.domain.PlaceRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    void deleteByPost(PlaceRecommendation post);
}
