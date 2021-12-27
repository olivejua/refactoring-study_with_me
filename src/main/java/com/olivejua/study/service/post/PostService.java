package com.olivejua.study.service.post;

import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.response.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    void validateAuthor(Post post, User author);

    List<String> uploadImages(List<MultipartFile> images, String postImagePath, Long postId);

    List<String> replaceImages(List<MultipartFile> images, String postImagePath, Post post);

    void removeImages(Post post);

    PageInfo toPageInfo(Page<? extends Post> posts);
}
