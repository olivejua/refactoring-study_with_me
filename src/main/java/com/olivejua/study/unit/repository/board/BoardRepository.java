package com.olivejua.study.unit.repository.board;

import com.olivejua.study.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
