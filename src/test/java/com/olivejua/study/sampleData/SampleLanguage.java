package com.olivejua.study.sampleData;

import com.olivejua.study.domain.board.Language;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SampleLanguage {

    public static List<Language> createList() {
        String[] languages = {"java", "spring", "jpa", "gcp", "mysql"};

        return Arrays.stream(languages)
                .map(Language::new)
                .collect(Collectors.toList());
    }
}
