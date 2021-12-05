package com.olivejua.study.domain.image;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
        this.urls.addAll(urls);
    }
}