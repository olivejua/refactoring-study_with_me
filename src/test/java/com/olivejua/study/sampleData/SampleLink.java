package com.olivejua.study.sampleData;

import com.olivejua.study.domain.board.Link;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SampleLink {

    public static List<Link> createList() {
        String[] links = {"www.google.com", "www.naver.com", "www.tistory.com", "www.daum.net", "www.github.com"};

        return Arrays.stream(links)
                .map(Link::new)
                .collect(Collectors.toList());
    }
}
