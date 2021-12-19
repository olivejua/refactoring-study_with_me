package com.olivejua.study.domain.image;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.olivejua.study.utils.ImagesUtil.extractPathFromUrl;
import static com.olivejua.study.utils.PostImagePaths.POSTS;

@NoArgsConstructor
@Embeddable
public class Images {

    @ElementCollection
    @CollectionTable(name = "POST_IMAGES",
            joinColumns = @JoinColumn(name = "POST_ID"))
    @BatchSize(size = 30)
    @Column(name = "IMAGE_URL", nullable = false)
    private final List<String> urls = new ArrayList<>();

    public void addAll(List<String> urls) {
        if (urls.isEmpty()) return;
        this.urls.addAll(urls);
    }

    public void replace(List<String> urls) {
        this.urls.clear();
        addAll(urls);
    }

    public List<String> getPaths() {
        return urls.stream()
                .map(url -> extractPathFromUrl(POSTS, url))
                .collect(Collectors.toList());
    }

    public boolean hasImages() {
        return !urls.isEmpty();
    }
}
