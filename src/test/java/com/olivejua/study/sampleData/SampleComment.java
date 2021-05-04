package com.olivejua.study.sampleData;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;

import java.util.ArrayList;
import java.util.List;

public class SampleComment {

    public static Comment create(User writer, Board post) {
        return Comment.builder()
                .post(post)
                .writer(writer)
                .content("참여하고 싶습니다")
                .build();
    }

    public static List<Comment> createList(User writer, Board post, int size) {
        List<Comment> comments = new ArrayList<>();

        for(int i=1; i<=size; i++) {
            comments.add(
                    Comment.builder()
                            .post(post)
                            .writer(writer)
                            .content("참여하고 싶습니다 -" + i)
                            .build()
            );
        }

        return comments;
    }
}
