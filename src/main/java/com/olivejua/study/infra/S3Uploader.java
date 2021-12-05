package com.olivejua.study.infra;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    /**
     * S3 스토리지로 파일을 업로드해줍니다.
     * @param multipartFile 유저가 업로드한 파일
     * @param key 저장될 경로명 (파일 확장자까지 포함)
     * @return 저장한 ImageUrl
     */
    public String upload(MultipartFile multipartFile, String key) {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return uploadToS3(uploadFile, key);
    }

    private String uploadToS3(File uploadFile, String key) {
        String uploadImageUrl = putS3(uploadFile, key);
        removeTempFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String key) {
        amazonS3.putObject(new PutObjectRequest(bucket, key, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, key).toString();
    }

    private void removeTempFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
            return;
        }
        log.info("파일이 삭제되지 못했습니다.");
    }

    private Optional<File> convert(MultipartFile file) {
        try {
            File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            log.warn("error: MultipartFile -> File convert fail");
        }

        return Optional.empty();
    }

    /**
     * S3에 저장되어 있는 1개의 객체를 삭제한다.
     * @param filePath bucket 이름을 제외한 파일 경로 (예 - user/profile/1/testImage.jpg)
     */
    public void remove(String filePath) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, filePath));
    }

    /**
     * S3에 저장되어 있는 2개 이상의 객체들을 삭제한다.
     * @param paths bucket 이름을 제외한 파일 경로 목록 (예 - List.of("post/1/testImage1.jpg", "post/1/testImage2.jpg", ...))
     * @return 삭제한 객체 개수
     */
    public int remove(List<String> paths) {
        List<DeleteObjectsRequest.KeyVersion> keys = mapToKeyVersion(paths);

        DeleteObjectsRequest request = new DeleteObjectsRequest(bucket)
                .withKeys(keys)
                .withQuiet(false);

        DeleteObjectsResult result = amazonS3.deleteObjects(request);
        return result.getDeletedObjects().size();
    }

    private List<DeleteObjectsRequest.KeyVersion> mapToKeyVersion(List<String> paths) {
        return paths.stream()
                .map(DeleteObjectsRequest.KeyVersion::new)
                .collect(Collectors.toList());
    }
}
