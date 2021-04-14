package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
