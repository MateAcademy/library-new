package com.example.demo.dto.media_classification;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlatformOperatingSystemVersionResponse(
        Long id,
        String platformName,
        String operatingSystemName,
        String operatingSystemVersion
) {
}
