package com.olivejua.study.service.post;

import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.exception.post.DifferentUserWithPostAuthorException;
import com.olivejua.study.response.PageInfo;
import com.olivejua.study.service.upload.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class DefaultPostService implements PostService {
    private final UploadService uploadService;

    @Override
    public void validateAuthor(Post post, User author) {
        if (post.hasSameAuthorAs(author)) {
            return;
        }

        throw new DifferentUserWithPostAuthorException();
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> images, String postImagePath, Long postId) {
        if (images.isEmpty()) return Collections.emptyList();

        return uploadService.upload(images, postImagePath + postId);
    }

    @Override
    public List<String> replaceImages(List<MultipartFile> images, String postImagePath, Post post) {
        removeImages(post);
        return uploadImages(images, postImagePath, post.getId());
    }

    @Override
    public void removeImages(Post post) {
        if (post.hasImages()) {
            uploadService.remove(post.getImagePaths());
        }
    }

    @Override
    public PageInfo toPageInfo(Page<? extends Post> posts) {
        return PageInfo.builder()
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .number(posts.getNumber())
                .first(posts.isFirst())
                .last(posts.isLast())
                .numberOfElements(posts.getNumberOfElements())
                .build();
    }
}
