package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.Language;
import com.olivejua.study.sampleData.SampleLanguage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LanguageRepositoryTest {

    @Autowired
    LanguageRepository languageRepository;

    @Test
    @DisplayName("Language - 저장")
    public void save() {
        List<Language> languages = SampleLanguage.createList();
        for (Language language : languages) {
            languageRepository.save(language);
        }

        List<Language> findLanguages = languageRepository.findAll();

        assertEquals(languages, findLanguages);
    }
}
