package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.repository.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommonBoardRepositoryTest extends CommonRepositoryTest {

    @Autowired
    protected CommentRepository commentRepository;

    protected User dummyWriter;
    protected List<Board> dummyPosts;
    protected List<Comment> dummyComment;

    @BeforeEach
    void setupCommon() {
        dummyWriter = createDummyUser();
        dummyPosts = createDummyPosts();
        dummyComment = createDummyComments(dummyPosts);
        setup();
    }

    @AfterEach
    void clearAllCommon() {
        commentRepository.deleteAll();
        clearAll();
        userRepository.deleteAll();
    }

    abstract void setup();
    abstract void clearAll();

    List<Board> createDummyPosts() {
        List<Board> posts = new ArrayList<>();

        for (int i=1; i<=20; i++) {
            posts.add(createDummyPost());
        }

        return posts;
    }

    abstract Board createDummyPost();

    protected List<Comment> createDummyComments(List<Board> posts) {
        return posts.stream()
                .map(post -> createDummyComment(post, dummyWriter))
                .collect(Collectors.toList());
    }

    protected Comment createDummyComment(Board post, User writer) {
        Comment comment = Comment.createComment(post, writer, "sample content");
        return commentRepository.save(comment);
    }
}
