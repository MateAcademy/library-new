package com.example.demo.dto.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlatformSortFieldEnum implements SortField {
    ID("id", false),
    NAME("name", true),
    DESCRIPTION("description", true),
    EXAMPLE("example", true);

    private final String field;
    private final boolean isString;
}
