package com.olivejua.study.web.dto.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ImageDto {
    private MultipartFile fileData;
    private String callback;
    private String callback_func;

    public ImageDto(MultipartFile fileData, String callback, String callback_func) {
        this.fileData = fileData;
        this.callback = callback;
        this.callback_func = callback_func;
    }
}
