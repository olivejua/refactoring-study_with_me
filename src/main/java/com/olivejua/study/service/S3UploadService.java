package com.olivejua.study.service;

import com.olivejua.study.infra.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService implements UploadService {
    private final S3Uploader uploader;

    @Override
    public List<String> upload(List<MultipartFile> files, String path) {
        List<String> savedUrls = new ArrayList<>();

        for (MultipartFile uploadFile : files) {
            String savedPath = makeUploadPath(path, uploadFile);

            String url = uploader.upload(uploadFile, savedPath);
            savedUrls.add(url);
        }

        return savedUrls;
    }

    private String makeUploadPath(String path, MultipartFile file) {
        return path + UUID.randomUUID() + file.getOriginalFilename();
    }

    @Override
    public int remove(List<String> paths) {
        if (paths==null || paths.isEmpty()) {
            log.info("삭제할 이미지가 없습니다.");
            return 0;
        }

        return uploader.remove(paths);
    }
}
