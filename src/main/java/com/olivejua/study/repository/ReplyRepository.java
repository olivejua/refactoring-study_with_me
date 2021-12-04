package com.olivejua.study.repository;

import com.olivejua.study.domain.Reply;
import com.olivejua.study.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    public void deleteRepliesByPost(Post post);
}
