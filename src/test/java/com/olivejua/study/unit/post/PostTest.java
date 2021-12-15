package com.olivejua.study.unit.post;

import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.question.Question;
import com.olivejua.study.domain.user.Role;
import com.olivejua.study.domain.user.SocialCode;
import com.olivejua.study.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PostTest {
    private final User AUTHOR = User.createUser(
            1L, "author", "author@gamil.com", Role.USER, SocialCode.GOOGLE);
    private final Post MOCK_POST = Question.createPost(
            1L, AUTHOR, "test title", "test content");

    @Test
    void testHasSameAuthorAs_True() {
        User sameUser = User.createUser(1L, "author", "author@gamil.com", Role.USER, SocialCode.GOOGLE);

        Assertions.assertTrue(MOCK_POST.hasSameAuthorAs(sameUser));
    }

    @Test
    void testHasSameAuthorAs_False() {
        User anotherUser = User.createUser(2L, "anotherUser", "anotherUser@gamil.com", Role.USER, SocialCode.GOOGLE);

        Assertions.assertFalse(MOCK_POST.hasSameAuthorAs(anotherUser));
    }
}
