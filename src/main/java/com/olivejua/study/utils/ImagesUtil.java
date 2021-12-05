package com.olivejua.study.utils;

import java.util.List;

public class ImagesUtil {
    private final static List<String> IMAGE_EXTENSIONS = List.of("gif", "jpeg", "jpg", "png", "bmp", "pdf", "mp4");

    public static String extractPathFromUrl(String path, String url) {
        int idx = url.lastIndexOf(path);
        return url.substring(idx);
    }
}
