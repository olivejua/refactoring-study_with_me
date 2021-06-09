package com.olivejua.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileService {

//    @Value("${spring.servlet.multipart.location}")
    private String fileUploadPath = "temp string";

    public String getFilePath() {
        return fileUploadPath;
    }
}
