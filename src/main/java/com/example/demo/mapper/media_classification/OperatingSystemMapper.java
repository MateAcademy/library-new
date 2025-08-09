package com.example.demo.mapper.media_classification;

import com.example.demo.dto.media_classification.PlatformOperatingSystemResponse;
import com.example.demo.model.media_classification.PlatformOperatingSystem;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class OperatingSystemMapper {

    @NotNull
    public PlatformOperatingSystemResponse mapToOperatingSystemResponse(@NotNull PlatformOperatingSystem platformOperatingSystem) {
        return new PlatformOperatingSystemResponse(
                platformOperatingSystem.getId(),
                platformOperatingSystem.getName()
        );
    }
}