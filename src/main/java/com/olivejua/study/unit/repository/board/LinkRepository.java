package com.olivejua.study.unit.repository.board;

import com.olivejua.study.domain.board.Link;
import com.olivejua.study.domain.board.PlaceRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    void deleteByPost(PlaceRecommendation post);
}
