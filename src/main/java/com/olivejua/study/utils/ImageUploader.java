package com.olivejua.study.utils;

import com.olivejua.study.config.ImageConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;

@Slf4j
@Component
public class ImageUploader {
    public static String tempPath = "";

    public void uploadImagesIn(List<String> imagePaths, String savedPath) {
        makeDirectory(savedPath);
        convertImages(imagePaths, tempPath, savedPath);
    }

    public void readImagesIn(String sourcePath, String targetPath) {
        convertAllInDir(sourcePath, targetPath);
    }

    public void updateImagesIn(List<String> imagePaths, String path) {
        deleteDirectory(path);
        uploadImagesIn(imagePaths, path);
    }

    public void transferImageByFile(MultipartFile image, String movedPath, String imageName) {
        try {
            makeDirectory(movedPath);
            File dest = new File(movedPath + imageName);
            image.transferTo(dest);
        } catch (IOException e) {
            log.error("[ImageUploader] 해당 디렉토리에 이미지를 이동시킬 수 없습니다.");
        }
    }

    public void deleteImagesIn(String path) {
        deleteDirectory(path);
    }

    private boolean deleteDirectory(String path) {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            log.error("[ImageUploader] 해당 디렉토리를 삭제할 수 없습니다. location=" + path);
            return false;
        }

        return true;
    }

    private void convertAllInDir(String sourceDir, String targetDir) {
        File source = new File(sourceDir);
        File target = new File(targetDir);

        try {
            FileUtils.copyDirectory(source, target);
        } catch(IOException e) {
            log.error("[ImageUploader] source 경로가 존재하지 않습니다.");
        }
    }

    private void convertImages(List<String> imageNames,
                               String sourcePath, String targetPath) {
        for (String name : imageNames) {
            convertImage(name, sourcePath, targetPath);
        }
    }

    private boolean convertImage(String imageName, String sourcePath, String targetPath) {
        Path source = Paths.get(sourcePath+imageName);
        Path target = Paths.get(targetPath+imageName);

        if (!validatePaths(source, target)) {
            return false;
        }

        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        boolean result = Files.exists(target);
        if (result) {
            deleteImage(sourcePath);
        }

        return result;
    }

    private void deleteImage(String path) {
        if (path == null) {
            return;
        }

        File delFile = new File(path);
        if (delFile.exists()) {
            delFile.delete();
        }
    }

    private boolean validatePaths(Path source, Path target) {
       if (tempPath.isEmpty()) {
            log.error("[ImageUploader] temp 경로가 비어있습니다.");
            return false;
        } else if (source==null || target==null) {
            log.error("[ImageUploader] source 또는 target 경로가 존재하지 않습니다.");
            return false;
        } else if (!Files.exists(source)) {
            log.error("[ImageUploader] source 경로가 존재하지 않습니다.");
            return false;
        } else if (source == target) {
            log.error("[ImageUploader] source와 target 경로가 같습니다.");
            return false;
        }

        return true;
    }

    private boolean makeDirectory(String path) {
        File savedPath = new File(path);
        return savedPath.mkdirs();
    }

    public String getDirectory(String... categories) {
        StringBuilder path =  new StringBuilder();
        for (String category : categories) {
            path.append(category)
                    .append(separator);
        }

        return path.toString();
    }
}
