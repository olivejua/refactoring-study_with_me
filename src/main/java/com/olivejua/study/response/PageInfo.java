package com.olivejua.study.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class PageInfo {
    private long totalElements; //총 컨텐츠 개수
    private int totalPages; //총 페이지 개수
    private int number; // 현재 페이지 수
    private boolean first; // 첫 페이지 여부
    private boolean last; // 마지막 페이지 여부
    private int numberOfElements; //현재 페이지 컨텐츠 개수

    @Builder
    public PageInfo(long totalElements, int totalPages, int number, boolean first, boolean last, int numberOfElements) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
        this.first = first;
        this.last = last;
        this.numberOfElements = numberOfElements;
    }
}
