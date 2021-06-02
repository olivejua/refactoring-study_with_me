package com.olivejua.study.service;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.Reply;
import com.olivejua.study.domain.User;
import com.olivejua.study.repository.CommentRepository;
import com.olivejua.study.repository.ReplyRepository;
import com.olivejua.study.web.dto.reply.ReplySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    public Long save(ReplySaveRequestDto requestDto, User writer) {
        Comment comment = findComment(requestDto.getCommentId());
        Reply reply = Reply.createReply(comment, writer, requestDto.getContent());

        replyRepository.save(reply);
        return reply.getId();
    }

    public Long update(Long replyId, String updatedContent) {
        Reply reply = findReply(replyId);
        reply.edit(updatedContent);

        return replyId;
    }

    public Long delete(Long replyId) {
        Reply reply = findReply(replyId);

        replyRepository.delete(reply);

        return replyId;
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. commentId=" + commentId));
    }

    private Reply findReply(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 없습니다. replyId = " + replyId));
    }
}
