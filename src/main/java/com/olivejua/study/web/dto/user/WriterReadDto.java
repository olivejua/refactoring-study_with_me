package com.olivejua.study.web.dto.user;

import com.olivejua.study.domain.User;
import lombok.Getter;

@Getter
public class WriterReadDto {
    private Long id;
    private String name;

    public WriterReadDto(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
