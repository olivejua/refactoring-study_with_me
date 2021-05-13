package com.olivejua.study.sampleData;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.PlaceRecommendation;

import java.util.Arrays;

public class SamplePlaceRecommendation {

    public static PlaceRecommendation create(User writer, String[] links) {
        return PlaceRecommendation.savePost(
                writer,
                "강남 스터디카페 추천합니다.",
                "서울시 강남구",
                "강남역 1번출구 노란건물 1층",
                "/abc/def/gh",
                "한번 가보시길.. 좋아요 눌러주세요.",
                Arrays.asList(links));
    }
}
