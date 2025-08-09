package com.example.demo.mapper.media_classification;

import com.example.demo.dto.media_classification.OperatingSystemVersionResponse;
import com.example.demo.dto.media_classification.PlatformOperatingSystemVersionResponse;
import com.example.demo.model.media_classification.OperatingSystemVersion;
import com.example.demo.model.media_classification.PlatformOperatingSystem;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OperatingSystemVersionMapper {

    @NotNull
    public OperatingSystemVersionResponse mapToOperatingSystemVersionResponse(@NotNull OperatingSystemVersion operatingSystemVersion) {
        return new OperatingSystemVersionResponse(
                operatingSystemVersion.getId(),
                operatingSystemVersion.getName()
        );
    }

    @NotNull
    public Set<PlatformOperatingSystemVersionResponse> mapOperatingSystemVersionToResponseList(@NotNull Set<OperatingSystemVersion> operatingSystemVersions) {
        return operatingSystemVersions.stream()
                .map(this::mapToPlatformOperatingSystemVersionResponse)
                .collect(Collectors.toSet());
    }

    @NotNull
    public PlatformOperatingSystemVersionResponse mapToPlatformOperatingSystemVersionResponse(@NotNull OperatingSystemVersion operatingSystemVersion) {
        PlatformOperatingSystem platformOperatingSystem = operatingSystemVersion.getPlatformOperatingSystem();

        return new PlatformOperatingSystemVersionResponse(
                operatingSystemVersion.getId(),
                platformOperatingSystem.getPlatform().getName(),
                platformOperatingSystem.getName(),
                operatingSystemVersion.getName()
        );
    }
}
