package com.olivejua.study.sampleData;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;

public class SampleComment {

    public static Comment create(User writer, Board post) {
        return Comment.builder()
                .post(post)
                .writer(writer)
                .content("참여하고 싶습니다")
                .build();
    }
}
