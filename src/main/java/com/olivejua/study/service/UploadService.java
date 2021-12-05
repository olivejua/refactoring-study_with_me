package com.olivejua.study.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {
    /**
     * 2개 이상의 파일을 업로드한다.
     * @param files 업로드 파일 목록
     * @param path 저장될 경로 (ex- post/{postId})
     * @return 저장된 fileUrl 목록
     */
    List<String> upload(List<MultipartFile> files, String path);

    /**
     * 2개 이상의 파일을 삭제한다.
     * @param paths 삭제할 파일 경로 목록
     * @return 삭제된 파일 개수
     */
    int remove(List<String> paths);

}
