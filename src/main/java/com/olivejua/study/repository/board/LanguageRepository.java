package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
