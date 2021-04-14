package com.olivejua.study.sampleData;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Link;
import com.olivejua.study.domain.board.PlaceRecommendation;

import java.util.List;

public class SamplePlaceRecommendation {

    public static PlaceRecommendation create(User writer, List<Link> links) {
        return PlaceRecommendation.builder()
                .writer(writer)
                .title("강남 스터디카페 추천합니다.")
                .address("서울시 강남구")
                .addressDetail("강남역 1번출구 노란건물 1층")
                .thumbnailPath("/abc/def/gh")
                .content("한번 가보시길.. 좋아요 눌러주세요.")
                .links(links)
                .build();
    }
}
