package com.example.demo.dto.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum IdNameSortFieldEnum implements SortField {
    ID("id", false),
    NAME("name", true);

    private final String field;
    private final boolean isString;
}
