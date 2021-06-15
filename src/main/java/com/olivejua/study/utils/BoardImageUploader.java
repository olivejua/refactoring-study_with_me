package com.olivejua.study.utils;

import com.olivejua.study.config.ImageConfig;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.domain.board.StudyRecruitment;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;

@Slf4j
@Component
public class BoardImageUploader {
    private final String STUDY_RECRUITMENT = "study";
    private final String PLACE_RECOMMENDATION = "place";
    private final String QUESTION = "question";
    private final ImageUploader imageUploader;
    private final String basePath;

    public BoardImageUploader(ImageUploader imageUploader, ImageConfig imageConfig) {
        this.imageUploader = imageUploader;
        this.basePath = imageConfig.getDefaultPath();
    }

    public void uploadThumbnailInPlace(MultipartFile thumbnail, PlaceRecommendation post) {
        String movedPath = getBoardPathOnBasePath(PLACE_RECOMMENDATION, post.getId());
        imageUploader.transferImageByFile(thumbnail, movedPath, post.getThumbnailName());
    }

    /**
     * images 저장
     */
    private void uploadImages(String htmlCode, String boardName, Long postId) {
        List<String> imagePaths = getImagePathsInHtml(htmlCode);
        if (imagePaths.isEmpty()) {
            return;
        }

        imageUploader.uploadImagesIn(imagePaths,
                getBoardPathOnBasePath(boardName, postId));
    }

    public void uploadImagesInQuestion(Question post) {
        uploadImages(post.getContent(), QUESTION, post.getId());
    }

    public void uploadImagesInStudy(StudyRecruitment post) {
        uploadImages(post.getCondition().getExplanation(),
                STUDY_RECRUITMENT, post.getId());
    }

    public void uploadImagesInPlace(PlaceRecommendation post) {
        uploadImages(post.getContent(), PLACE_RECOMMENDATION, post.getId());
    }

    /**
     * image 읽기
     */
    private void readImages(String htmlCode, String servletPath, String boardName, Long postId) {
        List<String> imagePaths = getImagePathsInHtml(htmlCode);

        if (imagePaths.isEmpty()) {
            return;
        }

        String sourcePath = getBoardPathOnBasePath(boardName, postId);
        String targetPath = servletPath + imageUploader.getDirectory("resource", "photo_upload");

        imageUploader.readImagesIn(sourcePath, targetPath);
    }

    public void readImagesInQuestion(Question post, String servletPath) {
        readImages(post.getContent(), servletPath, QUESTION, post.getId());
    }

    public void readImagesInStudy(StudyRecruitment post, String servletPath) {
        readImages(post.getCondition().getExplanation(),
                servletPath, STUDY_RECRUITMENT, post.getId());
    }

    public void readImagesInPlace(PlaceRecommendation post, String servletPath) {
        readImages(post.getContent(), servletPath, PLACE_RECOMMENDATION, post.getId());
    }

    /**
     * images 수정
     */
    private void updateImages(String htmlCode, String boardName, Long postId) {
        List<String> imagePaths = getImagePathsInHtml(htmlCode);
        if (imagePaths.isEmpty()) {
            return;
        }

        imageUploader.updateImagesIn(imagePaths,
                getBoardPathOnBasePath(boardName, postId));
    }

    public void updateImagesInQuestion(Question post) {
        updateImages(post.getContent(), QUESTION, post.getId());
    }

    public void updateImagesInStudy(StudyRecruitment post) {
        updateImages(post.getCondition().getExplanation(),
                STUDY_RECRUITMENT, post.getId());
    }

    public void updateImagesInPlace(PlaceRecommendation post) {
        updateImages(post.getContent(), PLACE_RECOMMENDATION, post.getId());
    }


    /**
     * images 삭제
     */
    private void deleteImages(String boardName, Long postId) {
        imageUploader.deleteImagesIn(
                getBoardPathOnBasePath(boardName, postId)
        );
    }

    public void deleteImagesInQuestion(Long postId) {
        deleteImages(QUESTION, postId);
    }

    public void deleteImagesInStudy(Long postId) {
        deleteImages(STUDY_RECRUITMENT, postId);
    }

    public void deleteImagesInPlace(Long postId) {
        deleteImages(PLACE_RECOMMENDATION, postId);
    }


    private String getBoardPathOnBasePath(String boardName, Long postId) {
        return basePath + separator +
                boardName + separator +
                postId.toString() + separator;
    }

    private List<String> getImagePathsInHtml(String htmlCode) {
        List<String> paths = new ArrayList<>();

        Document doc = Jsoup.parse(htmlCode);
        Elements images = doc.getElementsByTag("img");

        for (Element image : images) {
            String name = image.attr("src")
                    .replace("/resource/photo_upload/", "");

            paths.add(name);
        }

        return paths;
    }
}
