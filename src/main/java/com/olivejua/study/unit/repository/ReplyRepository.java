package com.olivejua.study.unit.repository;

import com.olivejua.study.domain.Reply;
import com.olivejua.study.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    public void deleteRepliesByPost(Board post);
}
