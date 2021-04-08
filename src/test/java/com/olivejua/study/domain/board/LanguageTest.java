package com.olivejua.study.domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LanguageTest {

    @Test
    @DisplayName("언어목록이 모두 같아야 true")
    public void equalsTest_success() throws Exception {
        List<Language> languages1 = createLanguages(new String[] {"java", "spring", "jpa", "aws", "h2"});
        List<Language> languages2 = createLanguages(new String[] {"java", "spring", "jpa", "aws", "h2"});

        assertThat(languages1.equals(languages2)).isTrue();
    }

    @Test
    @DisplayName("언어목록이 하나라도 다르면 false")
    public void equalsTest_fail() throws Exception {
        List<Language> languages1 = createLanguages(new String[] {"java", "spring", "jpa", "aws", "h2"});
        List<Language> languages2 = createLanguages(new String[] {"java", "spring", "jpa", "gcp", "h2"});

        assertThat(languages1.equals(languages2)).isFalse();
    }

    public static List<Language> createLanguages(String[] words) {
        return Arrays.stream(words)
                .map(Language::new)
                .collect(Collectors.toList());
    }
}
