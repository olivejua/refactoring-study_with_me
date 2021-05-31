package com.olivejua.study.service;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.repository.CommentRepository;
import com.olivejua.study.repository.board.BoardRepository;
import com.olivejua.study.web.dto.comment.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentSaveRequestDto requestDto, User writer) {
        Board post = findPost(requestDto.getPostId());
        Comment comment = Comment.createComment(post, writer, requestDto.getContent());

        commentRepository.save(comment);

        return comment.getId();
    }

    public Long update(Long commentId, String updatedContent) {
        Comment comment = findComment(commentId);
        comment.edit(updatedContent);

        return commentId;
    }

    public Long delete(Long commentId) {
        Comment comment = findComment(commentId);
        
        //reply 먼저 지우기
        
        commentRepository.delete(comment);

        return commentId;
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. commentId=" + commentId));
    }

    private Board findPost(Long postId) {
        return boardRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postId=" + postId));
    }
}
