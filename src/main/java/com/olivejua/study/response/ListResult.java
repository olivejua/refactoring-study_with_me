package com.olivejua.study.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ListResult<T> extends SuccessResult {
    private final List<T> content = new ArrayList<>();
    private PageInfo pageInfo;

    public ListResult(List<T> list) {
        success();
        content.addAll(list);
    }

    public ListResult(List<T> list, PageInfo pageInfo) {
        this(list);
        this.pageInfo = pageInfo;
    }

    private PageInfo toPageInfo(Page<T> pageList) {
        return PageInfo.builder()
                .totalElements(pageList.getTotalElements())
                .totalPages(pageList.getTotalPages())
                .number(pageList.getNumber())
                .first(pageList.isFirst())
                .last(pageList.isLast())
                .numberOfElements(pageList.getNumberOfElements())
                .build();
    }
}
