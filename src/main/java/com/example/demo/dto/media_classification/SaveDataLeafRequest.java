package com.example.demo.dto.media_classification;

import com.example.demo.model.media_classification.DocumentLeafType;

import java.util.Set;

public record SaveDataLeafRequest(
    DocumentLeafType docType,
    Long docId,
    Set<Long> platformIds,
    Set<Long> posIds,
    Set<Long> versionIds
) {
}
