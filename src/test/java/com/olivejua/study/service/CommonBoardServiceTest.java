package com.olivejua.study.service;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.Reply;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.repository.CommentRepository;
import com.olivejua.study.repository.ReplyRepository;
import com.olivejua.study.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommonBoardServiceTest extends CommonServiceTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected ReplyRepository replyRepository;

    protected User dummyPostWriter;

    protected Board dummyPost;

    @BeforeEach
    void setup() {
        dummyPostWriter = User.createUser("post writer", "postWriter@gmail.com", Role.USER, "google");
        userRepository.save(dummyPostWriter);

        saveDummyPost(createDummyPost());
        saveDummyComments();
    }

    abstract void saveDummyPost(Board post);
    abstract Board createDummyPost();

    protected void saveDummyComments() {
        User commentWriter1 = User.createUser("user2", "user2@gmail.com", Role.USER, "google");
        User commentWriter2 = User.createUser("user3", "user3@gmail.com", Role.USER, "google");
        userRepository.save(commentWriter1);
        userRepository.save(commentWriter2);

        Comment comment1 = Comment.createComment(dummyPost, commentWriter1, "sample content1");
        Comment comment2 = Comment.createComment(dummyPost, commentWriter1, "sample content2");
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        saveDummyReplies(comment1, comment2);
    }

    protected void saveDummyReplies(Comment... comments) {
        int count = 1;

        for (Comment comment : comments) {
            User replyWriter1 = User.createUser("replyUser"+count, "replyUser"+count+"@gmail.com", Role.USER, "google");
            Reply reply1 = Reply.createReply(comment, replyWriter1, "sample reply content"+(count++));
            userRepository.save(replyWriter1);
            replyRepository.save(reply1);

            User replyWriter2 = User.createUser("replyUser"+count, "replyUser"+(count++)+"@gmail.com", Role.USER, "google");
            Reply reply2 = Reply.createReply(comment, replyWriter2, "sample reply content"+(count++));
            userRepository.save(replyWriter2);
            replyRepository.save(reply2);
        }
    }
}
