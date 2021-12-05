package com.olivejua.study.repository;

import com.olivejua.study.domain.comment.Comment;
import com.olivejua.study.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentsByPost(Post post);
}
