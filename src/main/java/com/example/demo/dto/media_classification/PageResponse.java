package com.example.demo.dto.media_classification;

import java.util.List;

public record PageResponse<T>(
        Integer currentPage,
        Long totalResult,
        Integer pageSize,
        String sortField,
        String sortDirection,
        List<T> preview,
        List<T> elements
) {
}
